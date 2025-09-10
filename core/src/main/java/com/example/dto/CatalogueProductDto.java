package com.example.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CatalogueProductDto(
        UUID productId,
        int priority
) {
}
