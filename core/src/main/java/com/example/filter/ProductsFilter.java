package com.example.filter;

import java.math.BigDecimal;

public record ProductsFilter(
        String title,
        Integer categoryId,
        BigDecimal price
        ) {
}
