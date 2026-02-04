package com.bank.app;

import com.bank.app.dto.health.HealthResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.test.web.servlet.MockMvc;

import com.bank.app.service.HealthService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class BankAppApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private HealthService healthService;

	@Test
	void contextLoads() {

	}

	@Test
	void healthServiceTest() {
		HealthResponse response = healthService.getHealth();

		assertNotNull(response);
		assertEquals("UP", response.getStatus());
		assertEquals("bank-app", response.getAppName());
		assertEquals("1.0.0", response.getVersion());
		assertNotNull(response.getTimestamp());
	}

	@Test
	void validationError_returns400_withStandardContract() throws Exception {
		mockMvc.perform(post("/test/validate")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{}"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
	}

	@Test
	void notFound_returns404_withStandardContract() throws Exception {
		mockMvc.perform(get("/test/not-found"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.code").value("NOT_FOUND"));
	}
}
