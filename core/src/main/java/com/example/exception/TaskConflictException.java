package com.example.exception;

public class TaskConflictException extends RuntimeException {
    public TaskConflictException(String message) {
        super(message);
    }
}
