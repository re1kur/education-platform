package com.example.exception;

public class CatalogueProductNotFoundException extends RuntimeException {
    public CatalogueProductNotFoundException(String message) {
        super(message);
    }
}
