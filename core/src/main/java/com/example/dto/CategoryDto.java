package com.example.dto;

import java.util.UUID;

public record CategoryDto(
        UUID id,
        String title,
        String description,
        String previewDescription,
        UUID titleImageId) {
}
