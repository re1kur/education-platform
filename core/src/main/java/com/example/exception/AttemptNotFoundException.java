package com.example.exception;

public class AttemptNotFoundException extends RuntimeException {
    public AttemptNotFoundException(String message) {
        super(message);
    }
}
