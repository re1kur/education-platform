package com.example.taskservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class EndpointsController {
    @Value("${spring.application.name}")
    private String serviceName;

    @GetMapping
    public ResponseEntity<String> greeting() {
        return ResponseEntity.ok().body("Greetings by %s".formatted(serviceName));
    }

    @GetMapping("/token")
    public ResponseEntity<String> testToken(
            @AuthenticationPrincipal Jwt jwt
            ) {
        return ResponseEntity.ok().body(jwt.getSubject());
    }
}
