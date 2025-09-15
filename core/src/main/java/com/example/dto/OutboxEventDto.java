package com.example.dto;

import com.example.enums.OutboxType;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record OutboxEventDto(
        UUID id,
        LocalDateTime createdAt,
        String payload,
        OutboxType type,
        LocalDateTime reservedTo
) {
}
