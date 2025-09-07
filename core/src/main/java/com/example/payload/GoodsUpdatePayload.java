package com.example.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record GoodsUpdatePayload(
        @NotNull @Positive Integer id,
        @NotBlank String title,
        @NotNull Boolean inStock,
        @NotNull @Positive Integer categoryId,
        String description,
        @NotNull @Positive BigDecimal price) {
}
