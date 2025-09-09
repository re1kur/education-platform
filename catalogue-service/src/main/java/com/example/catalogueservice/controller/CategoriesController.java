package com.example.catalogueservice.controller;

import com.example.dto.CategoryDto;
import com.example.dto.PageDto;
import com.example.filter.CategoriesFilter;
import com.example.payload.CategoryPayload;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import com.example.catalogueservice.service.CategoryService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoriesController {
    private final CategoryService service;

    @PostMapping
    public ResponseEntity<?> create(
            @RequestPart(name = "payload") @Valid CategoryPayload payload,
            @RequestPart(name = "file") @Nullable MultipartFile titleImage,
            @AuthenticationPrincipal Jwt jwt
            ) {
        CategoryDto body = service.create(payload, titleImage, jwt);
        return ResponseEntity.ok(body);
    }

    @GetMapping
    public ResponseEntity<?> readAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @ModelAttribute @Nullable CategoriesFilter filter
    ) {
        PageDto<CategoryDto> body = service.readAll(filter, page, size);
        return ResponseEntity.ok(body);
    }
}
