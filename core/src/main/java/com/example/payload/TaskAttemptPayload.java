package com.example.payload;

import org.springframework.lang.Nullable;

public record TaskAttemptPayload(
        @Nullable String comment
) {
}
