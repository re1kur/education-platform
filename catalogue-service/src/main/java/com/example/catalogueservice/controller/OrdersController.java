package com.example.catalogueservice.controller;

import com.example.catalogueservice.service.OrderService;
import com.example.dto.OrderDto;
import com.example.dto.PageDto;
import com.example.filter.OrderFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrdersController {
    private final OrderService service;

    @PostMapping
    public ResponseEntity<?> create(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam("productId") List<UUID> productIds
            ) {
        OrderDto body = service.create(jwt, productIds);
        return ResponseEntity.ok(body);
    }

    @GetMapping
    public ResponseEntity<?> readAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @AuthenticationPrincipal Jwt jwt
    ) {
        PageDto<OrderDto> body = service.readAllByUser(page, size, jwt);
        return ResponseEntity.ok(body);
    }
}
