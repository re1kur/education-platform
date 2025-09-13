package com.example.exception;

public class ProductConflictException extends RuntimeException {
    public ProductConflictException(String message) {
        super(message);
    }
}
