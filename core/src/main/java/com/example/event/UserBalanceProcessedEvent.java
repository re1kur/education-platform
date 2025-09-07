package com.example.event;

public record UserBalanceProcessedEvent (String orderId, String transactionId) {
}
