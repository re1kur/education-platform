package com.example.command;

import java.math.BigDecimal;

public record CreateTransactionCommand (String orderId, String userId, BigDecimal amount, String transactionType) {
}
