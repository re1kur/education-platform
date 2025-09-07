package com.example.dto;

import java.math.BigDecimal;
import java.util.Objects;

public record GoodsDto(
        Integer id,
        String title,
        Integer categoryId,
        String description,
        BigDecimal price,
        Boolean inStock,
        String imageUrl) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GoodsDto)) return false;
        GoodsDto that = (GoodsDto) o;
        return Objects.equals(this.id, that.id);
    }
}
