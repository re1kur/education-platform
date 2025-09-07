package com.example.dto;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record TaskAttemptDto(
        UUID id,
        UUID taskId,
        UUID userId,
        String status,
        String comment,
        List<UUID> fileIds
) {
}
