package com.example.payload;

import org.springframework.lang.Nullable;

public record TaskAttemptResultPayload(
        @Nullable String comment
) {
}
