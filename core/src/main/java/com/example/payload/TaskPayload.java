package com.example.payload;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record TaskPayload (
    @NotBlank @Size(min = 5, max = 128) String name,
    @NotBlank String description,
    @NotBlank String previewDescription,
    @Positive int cost
) {

}
