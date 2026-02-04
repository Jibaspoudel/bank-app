package com.bank.app;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenApiConfigTest {

    @Autowired
    private OpenAPI openAPI;

    @Test
    void openApiConfig_containsExpectedMetadata() {
        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());

        assertEquals("Bank App API", openAPI.getInfo().getTitle());
        assertEquals("v1", openAPI.getInfo().getVersion());
        assertEquals("Bank Application REST APIs", openAPI.getInfo().getDescription());
    }
}
