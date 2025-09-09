package com.example.catalogueservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestEndpointsController {
    @Value("${spring.application.name}")
    private String serviceName;

    @GetMapping
    public ResponseEntity<?> greetings() {
        return ResponseEntity.ok("Greetings by %s.".formatted(serviceName));
    }

    @GetMapping("/token")
    public ResponseEntity<?> checkToken(
            @AuthenticationPrincipal Jwt jwt
            ) {
        return ResponseEntity.ok(jwt.getSubject());
    }
}
