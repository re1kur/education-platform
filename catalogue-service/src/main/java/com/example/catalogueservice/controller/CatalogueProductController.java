package com.example.catalogueservice.controller;

import com.example.catalogueservice.service.CatalogueService;
import com.example.dto.CatalogueProductDto;
import com.example.payload.UpdateCataloguePayloadPayload;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalogue/{id}")
@RequiredArgsConstructor
public class CatalogueProductController {
    private final CatalogueService service;

    @DeleteMapping
    public ResponseEntity<?> delete(
            @PathVariable(name = "id") UUID id,
            @AuthenticationPrincipal Jwt jwt
            ) {
        service.delete(id, jwt);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<?> update(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable(name = "id") UUID id,
            @RequestBody @Valid UpdateCataloguePayloadPayload payload
    ) {
        CatalogueProductDto body = service.update(id, payload, jwt);
        return ResponseEntity.ok(body);
    }
}
