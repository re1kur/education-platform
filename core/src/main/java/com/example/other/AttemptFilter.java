package com.example.other;


import java.util.UUID;


public record AttemptFilter(
        UUID taskId,
        UUID userId,
        TaskAttemptStatus status
) {
}
