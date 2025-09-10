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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrdersController {
    private final OrderService service;

    public ResponseEntity<?> create(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam("productId") UUID... productIds
            ) {
        OrderDto body = service.create(jwt, productIds);
        return ResponseEntity.ok(body);
    }

    public ResponseEntity<?> readAll(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size,
            @ModelAttribute @Nullable OrderFilter filter
    ) {
        PageDto<OrderDto> body = service.readAll(page, size, filter);
        return ResponseEntity.ok(body);
    }
}
