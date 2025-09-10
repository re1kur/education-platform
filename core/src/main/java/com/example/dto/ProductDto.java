package com.example.dto;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record ProductDto(
        UUID id,
        String title,
        Integer price,
        boolean forSale,
        boolean single,
        String previewDescription,
        String description,
        UUID categoryId,
        List<UUID> imageIds
) {
}
