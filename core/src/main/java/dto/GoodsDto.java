package dto;

import java.math.BigDecimal;

public record GoodsDto(
        Integer id,
        String title,
        Integer categoryId,
        String description,
        BigDecimal price,
        Boolean inStock,
        String imageUrl) {
}
