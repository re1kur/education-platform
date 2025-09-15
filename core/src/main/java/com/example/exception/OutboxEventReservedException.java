package com.example.exception;

public class OutboxEventReservedException extends RuntimeException {
    public OutboxEventReservedException(String message) {
        super(message);
    }
}
