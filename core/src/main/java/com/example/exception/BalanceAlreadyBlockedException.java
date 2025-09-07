package com.example.exception;

public class BalanceAlreadyBlockedException extends Exception{
    public BalanceAlreadyBlockedException(String message) {
        super(message);
    }
}
