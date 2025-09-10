package com.example.payload;

import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.lang.Nullable;

public record UpdateCataloguePayloadPayload(
        @PositiveOrZero @Nullable Integer priority) {
}
