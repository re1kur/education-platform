package com.example.command;

import java.math.BigDecimal;

public record BlockUserBalanceCommand (
        String orderId,
        String userId,
        BigDecimal price,
        String transactionType) {
}
