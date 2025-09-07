package com.example.other;

import java.math.BigDecimal;

public record TaskFilter (
        String name,
        Integer trackId,
        BigDecimal cost
) {
}
