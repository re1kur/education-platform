package com.example.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public record TaskPayload (
    @NotBlank @Schema @Size(min = 5, max = 128) String name,
    @NotBlank @Schema String description,
    @NotBlank @Schema String previewDescription,
    @Positive @Schema int cost
) {

}
