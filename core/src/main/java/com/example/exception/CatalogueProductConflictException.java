package com.example.exception;

public class CatalogueProductConflictException extends RuntimeException {
    public CatalogueProductConflictException(String message) {
        super(message);
    }
}
