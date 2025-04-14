package payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateCatalogueGoodsPayload(
        @NotNull @Positive Integer goodsId,
        @PositiveOrZero Integer order) {
}
