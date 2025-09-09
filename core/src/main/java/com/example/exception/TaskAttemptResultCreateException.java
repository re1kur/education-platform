package com.example.exception;

public class TaskAttemptResultCreateException extends RuntimeException {
    public TaskAttemptResultCreateException(String message) {
        super(message);
    }
}
