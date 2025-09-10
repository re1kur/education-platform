package com.example.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CategoryDto(
        UUID id,
        String title,
        String description,
        String previewDescription,
        UUID titleImageId) {
}
