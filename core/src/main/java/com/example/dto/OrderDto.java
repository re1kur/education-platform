package com.example.dto;

import com.example.enums.OrderStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record OrderDto(
    UUID id,
    UUID userId,
    OrderStatus status,
    UUID transactionId,
    LocalDateTime createdAt,
    List<UUID> productIds,
    Integer amount
) {
}
