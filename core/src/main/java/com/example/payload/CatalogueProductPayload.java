package com.example.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.lang.Nullable;

import java.util.UUID;

public record CatalogueProductPayload(
        @NotNull UUID productId,
        @PositiveOrZero @Nullable Integer priority
) {
}
