package com.example.catalogueservice.exception;

public class GoodsAlreadyExistException extends Exception {
    public GoodsAlreadyExistException(String message) {
        super(message);
    }
}
