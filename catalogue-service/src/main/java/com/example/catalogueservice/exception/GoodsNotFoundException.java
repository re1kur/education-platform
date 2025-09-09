package com.example.catalogueservice.exception;

public class GoodsNotFoundException extends Exception {
    public GoodsNotFoundException(String message) {
        super(message);
    }
}
