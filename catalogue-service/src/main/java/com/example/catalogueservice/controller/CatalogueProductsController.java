package com.example.catalogueservice.controller;

import com.example.dto.CatalogueProductDto;
import com.example.dto.PageDto;
import com.example.dto.ProductDto;
import com.example.payload.CatalogueProductPayload;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import com.example.catalogueservice.service.CatalogueService;

@RestController
@RequestMapping("/api/v1/catalogue")
@RequiredArgsConstructor
public class CatalogueProductsController {
    private final CatalogueService service;

    @GetMapping
    public ResponseEntity<?> getCatalogue(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        PageDto<ProductDto> body = service.readAll(page, size);
        return ResponseEntity.ok(body);
    }

    @PostMapping
    public ResponseEntity<?> create(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody @Valid CatalogueProductPayload payload
    ) {
        CatalogueProductDto body = service.create(payload, jwt);
        return ResponseEntity.ok(body);
    }
}
