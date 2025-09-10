package com.example.dto;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record TaskDto(
        UUID id,
        String name,
        String description,
        String previewDescription,
        int cost,
        List<UUID> fileIds
) {
}
