package com.example.dto;


import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record TransactionDto(
        UUID id,
        UUID accountId,
        LocalDateTime createdAt,
        LocalDateTime executedAt,
        String type,
        String status,
        int amount,
        String referenceType,
        UUID referenceId
) {
}