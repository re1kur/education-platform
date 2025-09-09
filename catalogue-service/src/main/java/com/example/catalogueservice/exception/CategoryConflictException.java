package com.example.catalogueservice.exception;

public class CategoryConflictException extends RuntimeException {
    public CategoryConflictException(String message) {
        super(message);
    }
}
