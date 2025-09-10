package com.example.filter;

import com.example.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderFilter(
        UUID userId,
        OrderStatus status,
        UUID transactionId,
        LocalDateTime createdAt
) {
}
