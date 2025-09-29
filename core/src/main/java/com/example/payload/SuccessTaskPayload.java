package com.example.payload;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SuccessTaskPayload(
        UUID userId,
        int cost
) {
}
