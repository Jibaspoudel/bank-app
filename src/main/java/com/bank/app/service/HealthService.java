package com.bank.app.service;

import com.bank.app.dto.health.HealthResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
public class HealthService {
    @Value("${spring.application.name:bank-app}")
    private String appName;

    @Value("${app.version:1.0.0}")
    private String appVersion;

    public HealthResponse getHealth() {
        return HealthResponse.builder()
                .status("UP")
                .appName(appName)
                .version(appVersion)
                .timestamp(Instant.now())
                .build();
    }

}
