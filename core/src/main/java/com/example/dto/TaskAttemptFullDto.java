package com.example.dto;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record TaskAttemptFullDto(
        UUID id,
        UUID taskId,
        UUID userId,
        String status,
        String comment,
        TaskAttemptResultDto result,
        List<UUID> fileIds
) {
}
