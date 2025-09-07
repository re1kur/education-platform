package com.example.exception;

public class TransactionTypeNotFoundException extends Exception {
    public TransactionTypeNotFoundException(String message) {
        super(message);
    }
}
