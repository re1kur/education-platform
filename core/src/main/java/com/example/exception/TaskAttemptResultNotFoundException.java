package com.example.exception;

public class TaskAttemptResultNotFoundException extends RuntimeException {
    public TaskAttemptResultNotFoundException(String message) {
        super(message);
    }
}
