package com.example.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record TaskAttemptResultDto(
        UUID checkedBy,
        LocalDateTime checkedAt,
        String comment
) {
}
