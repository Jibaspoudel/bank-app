package com.bank.app.dto.health;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class HealthResponse {
    private String status;
    private String appName;
    private String version;
    private Instant timestamp;

}
