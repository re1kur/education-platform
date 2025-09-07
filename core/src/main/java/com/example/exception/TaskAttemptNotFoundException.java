package com.example.exception;

public class TaskAttemptNotFoundException extends RuntimeException {
    public TaskAttemptNotFoundException(String message) {
        super(message);
    }
}
