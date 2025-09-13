package com.example.filter;

import java.util.UUID;

public record ProductsFilter(
        String title,
        UUID categoryId,
        Integer price,
        Boolean single
        ) {
}
