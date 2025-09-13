package com.example.catalogueservice.controller;

import com.example.catalogueservice.service.OrderService;
import com.example.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders/{id}")
public class OrderController {
    private final OrderService service;

    @GetMapping
    public ResponseEntity<?> read(
            @PathVariable(name = "id") UUID id
            ) {
        OrderDto body = service.read(id);
        return ResponseEntity.ok(body);
    }

    @PutMapping
    public ResponseEntity<?> update(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable(name = "id") UUID id,
            @RequestParam(name = "productId") List<UUID> productIds
            ) {
        OrderDto body = service.update(id, productIds, jwt);
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
