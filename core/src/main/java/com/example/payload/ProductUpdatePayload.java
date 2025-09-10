package com.example.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductUpdatePayload(
        @NotBlank @Size(max = 128) String title,
        @Positive @NotNull Integer price,
        Boolean single,
        String description,
        @Size(max = 256) @Nullable String previewDescription,
        @NotNull UUID categoryId
) {
}
