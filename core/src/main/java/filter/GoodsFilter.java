package filter;

import java.math.BigDecimal;

public record GoodsFilter(
        String title,
        Integer categoryId,
        BigDecimal price
        ) {
}
