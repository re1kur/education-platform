package com.example.event;

import java.math.BigDecimal;

public record TransactionCreatedEvent (
        String orderId,
        String userId,
        String transactionId,
        String transactionType,
        BigDecimal amount
) {
}
