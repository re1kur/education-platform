package com.example.exception;

public class FileCountLimitExceededException extends RuntimeException {
    public FileCountLimitExceededException(String message) {
        super(message);
    }
}
