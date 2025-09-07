package com.example.exception;

public class NotEnoughCoinsOnBalanceException extends Exception {
    public NotEnoughCoinsOnBalanceException(String message) {
        super(message);
    }
}
