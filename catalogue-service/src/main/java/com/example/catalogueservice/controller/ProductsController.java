package com.example.catalogueservice.controller;

import com.example.dto.PageDto;
import com.example.dto.ProductDto;
import com.example.filter.ProductsFilter;
import com.example.payload.ProductPayload;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import com.example.catalogueservice.service.ProductService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductsController {
    private final ProductService service;

    @PostMapping
    public ResponseEntity<?> create(
            @AuthenticationPrincipal Jwt jwt,
            @RequestPart(name = "payload") @Valid ProductPayload payload,
            @RequestPart(name = "files")  MultipartFile[] files
            ) {
        ProductDto body = service.create(payload, files, jwt);
        return ResponseEntity.ok(body);
    }

    @GetMapping
    public ResponseEntity<?> readAll(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @ModelAttribute @Nullable ProductsFilter filter
    ) {
        PageDto<ProductDto> body = service.readAll(filter, page, size);
        return ResponseEntity.ok(body);
    }
}
