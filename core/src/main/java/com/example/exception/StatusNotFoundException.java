package com.example.exception;

public class StatusNotFoundException extends Exception {
    public StatusNotFoundException(String message) {
        super(message);
    }
}
