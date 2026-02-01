package com.bank.app.exception;

import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
public class ApiError {
    private Instant timestamp;
    private int status;
    private String code;
    private String message;
    private String path;
    private String traceId;
    private List<String> details;

    public ApiError(int status, String code, String message, String path, String traceId, List<String> details) {
        this.timestamp = Instant.now();
        this.status = status;
        this.code = code;
        this.message = message;
        this.path = path;
        this.traceId = traceId;
        this.details = details;
    }


}
