package com.example.event;

public record CompleteTransactionCommand (String orderId, String transactionId) {
}
