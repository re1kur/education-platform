package com.example.dto;

import java.math.BigDecimal;

public record BalanceDto(
        String userId,
        BigDecimal balance
) {
}
