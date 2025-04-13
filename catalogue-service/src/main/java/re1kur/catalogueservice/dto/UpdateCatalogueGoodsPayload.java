package re1kur.catalogueservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateCatalogueGoodsPayload(
        @NotNull Integer goodsId,
        @PositiveOrZero Integer order) {
}
