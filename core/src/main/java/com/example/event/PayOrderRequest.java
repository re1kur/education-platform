package com.example.event;

import lombok.Builder;

import java.util.UUID;

@Builder
public record PayOrderRequest(
        UUID orderId,
        UUID userId,
        Integer amount
) implements OutboxEventPayload {
}