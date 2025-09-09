package com.example.exception;

public class FileUpdateException extends RuntimeException {
    public FileUpdateException(String message) {
        super(message);
    }
}
