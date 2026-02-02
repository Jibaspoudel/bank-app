package com.bank.app;

import com.bank.app.dto.health.HealthResponse;
import com.bank.app.exception.NotFoundException;
import com.bank.app.service.HealthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class BankAppApplicationTests {

	@Autowired
	private HealthService healthService;

	@Autowired
	private org.springframework.test.web.servlet.MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	// ---------- EXISTING TESTS ----------

	@Test
	void contextLoads() {}

	@Test
	void healthServiceTest() {
		HealthResponse response = healthService.getHealth();

		assertNotNull(response);
		assertEquals("UP", response.getStatus());
		assertEquals("bank-app", response.getAppName());
		assertEquals("1.0.0", response.getVersion());
		assertNotNull(response.getTimestamp());
	}


	@RestController
	@RequestMapping("/test")
	static class TestController {

		@PostMapping("/validate")
		public Map<String, String> validate(@Valid @RequestBody CreateUserRequest req) {
			return Map.of("ok", "true");
		}

		@GetMapping("/notfound")
		public void notFound() {
			throw new NotFoundException("User not found with id 99");
		}
	}

	static class CreateUserRequest {

		@NotBlank(message = "Name is required")
		public String name;

		@Email(message = "Email must be valid")
		public String email;
	}


	@Test
	void invalidRequest_returns400_withValidationErrorAndDetails() throws Exception {

		var payload = Map.of(
				"name", "",
				"email", "not-an-email"
		);

		mockMvc.perform(post("/test/validate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(payload)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
				.andExpect(jsonPath("$.details").isArray())
				.andExpect(jsonPath("$.details[*]", hasItem(containsString("name"))))
				.andExpect(jsonPath("$.details[*]", hasItem(containsString("email"))));
	}

	@Test
	void unknownId_returns404_withNotFoundCode() throws Exception {

		mockMvc.perform(get("/test/notfound"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.code").value("NOT_FOUND"))
				.andExpect(jsonPath("$.message").value("User not found with id 99"));
	}
}
