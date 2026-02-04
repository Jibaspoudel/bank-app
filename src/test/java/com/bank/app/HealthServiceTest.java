package com.bank.app;

import com.bank.app.dto.health.HealthResponse;
import com.bank.app.service.HealthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class HealthServiceTest {

    @Autowired
    private HealthService healthService;

    @Test
    void healthServiceTest() {
        HealthResponse response = healthService.getHealth();

        assertNotNull(response);
        assertEquals("UP", response.getStatus());
        assertEquals("bank-app", response.getAppName());
        assertEquals("1.0.0", response.getVersion());
        assertNotNull(response.getTimestamp());
    }
}
