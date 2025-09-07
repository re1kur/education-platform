package com.example.event;

public record TransactionCreateFailedEvent(String orderId, String userId) {
}
