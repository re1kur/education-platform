package com.example.exception;

public class FileReadException extends RuntimeException {
    public FileReadException(String message) {
        super(message);
    }
}
