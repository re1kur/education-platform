package com.example.exception;

public class NotEnoughBalanceOnAccountException extends RuntimeException {
    public NotEnoughBalanceOnAccountException(String message) {
        super(message);
    }
}
