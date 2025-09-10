package com.example.catalogueservice.controller;

import com.example.catalogueservice.exception.ProductNotFoundException;
import com.example.catalogueservice.service.ProductService;
import com.example.dto.ProductDto;
import com.example.payload.ProductUpdatePayload;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products/{id}")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @GetMapping
    public ResponseEntity<?> read(
            @PathVariable(name = "id") UUID id
    ) {
        ProductDto body = service.read(id);
        return ResponseEntity.ok(body);
    }

    @PutMapping
    public ResponseEntity<?> update(
            @PathVariable(name = "id") UUID id,
            @RequestBody @Valid ProductUpdatePayload payload,
            @AuthenticationPrincipal Jwt jwt
    ) {
        ProductDto body = service.update(id, payload, jwt);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(
            @PathVariable(name = "id") UUID id,
            @AuthenticationPrincipal Jwt jwt
            ) {
        service.delete(id, jwt);
        return ResponseEntity.noContent().build();
    }
}