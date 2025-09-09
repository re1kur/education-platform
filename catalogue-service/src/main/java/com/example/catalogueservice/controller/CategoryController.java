package com.example.catalogueservice.controller;

import com.example.catalogueservice.service.CategoryService;
import com.example.dto.CategoryDto;
import com.example.payload.CategoryUpdatePayload;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories/{id}")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService service;

    @GetMapping
    public ResponseEntity<?> read(
            @PathVariable(name = "id") UUID id
    ) {
        CategoryDto body = service.read(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(body);
    }

    @PutMapping
    public ResponseEntity<?> update(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable(name = "id") UUID id,
            @RequestBody @Valid CategoryUpdatePayload payload
    ) {
        CategoryDto body = service.update(id, payload, jwt);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable(name = "id") UUID id
    ) {
        service.delete(id, jwt);
        return ResponseEntity.noContent().build();
    }
}
