package com.example.payload;

import com.example.enums.TaskAttemptStatus;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

public record TaskAttemptResultPayload(
        @NotNull TaskAttemptStatus status,
        @Nullable String comment
) {
}
