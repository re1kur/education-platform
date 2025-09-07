package com.example.dto;

import java.util.UUID;

public record TaskDto(
        UUID id,
        String name,
        String description,
        String previewDescription,
        int cost
) {
}
