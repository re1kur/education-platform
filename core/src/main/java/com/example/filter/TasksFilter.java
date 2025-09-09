package com.example.filter;

import java.math.BigDecimal;

public record TasksFilter(
        String name,
        Integer trackId,
        BigDecimal cost
) {
}
