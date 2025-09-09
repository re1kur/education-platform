package com.example.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.lang.Nullable;

public record CategoryPayload(
        @Size(max = 128) @NotBlank String title,
        @Size(max = 256) @Nullable String previewDescription,
        @Nullable String description
) {
}
