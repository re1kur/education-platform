package com.example.catalogueservice.exception;

public class CatalogueGoodsAlreadyExistException extends Exception {
    public CatalogueGoodsAlreadyExistException(String message) {
        super(message);
    }
}
