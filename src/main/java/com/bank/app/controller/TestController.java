package com.bank.app.controller;

import com.bank.app.exception.NotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {
    @PostMapping("/validate")
    public void validate(@Valid @RequestBody TestRequest request) {
    }

    @GetMapping("/not-found")
    public void notFound() {
        throw new NotFoundException("Test resource not found");
    }

    public static class TestRequest {
        @NotBlank
        private String name;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    @GetMapping("/business-error")
    public void businessError() {
        throw new com.bank.app.exception.BusinessRuleException("Test business rule violation");
    }
}
