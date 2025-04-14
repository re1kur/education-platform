package payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderPayload (
        @NotNull @Positive String userId,
        @NotNull @Positive Integer goodsId) {
}
