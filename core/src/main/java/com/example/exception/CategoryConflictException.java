package com.example.exception;

public class CategoryConflictException extends RuntimeException {
    public CategoryConflictException(String message) {
        super(message);
    }
}
