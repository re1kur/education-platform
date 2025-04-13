package re1kur.catalogueservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record GoodsUpdatePayload(
        @NotNull Integer id,
        @NotBlank String title,
        @NotNull Boolean inStock,
        @NotNull Integer categoryId,
        String description,
        @Positive BigDecimal price) {
}
