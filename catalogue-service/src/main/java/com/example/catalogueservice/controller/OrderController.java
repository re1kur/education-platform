package com.example.catalogueservice.controller;

import com.example.catalogueservice.service.OrderService;
import com.example.dto.OrderDto;
import com.example.payload.OrderPayload;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @PathVariable(name = "id") UUID id,
            @RequestBody @Valid OrderPayload payload,
            @RequestParam(name = "productId") UUID... productIds
            ) {
        OrderDto body = service.read(id, productIds);
        return ResponseEntity.ok(body);
    }
}
