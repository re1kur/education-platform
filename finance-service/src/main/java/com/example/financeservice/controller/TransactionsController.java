package com.example.financeservice.controller;

import com.example.dto.PageDto;
import com.example.dto.TransactionDto;
import com.example.financeservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionsController {
    private final TransactionService service;

    @GetMapping
    public ResponseEntity<?> readAll(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
            ) {
        PageDto<TransactionDto> body = service.readAll(jwt, page, size);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> read(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable(name = "id") UUID id
            ) {
        TransactionDto body = service.read(jwt, id);
        return ResponseEntity.ok(body);
    }
}
