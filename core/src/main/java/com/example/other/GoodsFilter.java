package com.example.other;

import java.math.BigDecimal;

public record GoodsFilter(
        String title,
        Integer categoryId,
        BigDecimal price
        ) {
}
