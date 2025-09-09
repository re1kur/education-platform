package com.example.filter;


import com.example.enums.TaskAttemptStatus;

import java.util.UUID;


public record AttemptsFilter(
        UUID taskId,
        UUID userId,
        TaskAttemptStatus status
) {
}
