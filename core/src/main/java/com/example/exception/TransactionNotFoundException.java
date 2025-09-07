package com.example.exception;

public class TransactionNotFoundException extends Exception {
    public TransactionNotFoundException(String message) {
        super(message);
    }
}
