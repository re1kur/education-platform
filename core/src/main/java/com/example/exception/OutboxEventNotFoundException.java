package com.example.exception;

public class OutboxEventNotFoundException extends RuntimeException {
    public OutboxEventNotFoundException(String message) {
        super(message);
    }
}
