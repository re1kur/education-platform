package com.example.catalogueservice.controller.advice;

import com.example.catalogueservice.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException() {
        return ResponseEntity.status(BAD_REQUEST).body("Category title must not be empty.");
    }

    @ExceptionHandler(GoodsNotFoundException.class)
    public ResponseEntity<String> handleGoodsNotFoundException(GoodsNotFoundException ex) {
        return ResponseEntity.status(BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<String> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        return ResponseEntity.status(BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(GoodsAlreadyExistException.class)
    public ResponseEntity<String> handleGoodsExistingException(GoodsAlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(CatalogueGoodsAlreadyExistException.class)
    public ResponseEntity<String> handleCatalogueGoodsExistingException(CatalogueGoodsAlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(CategoryConflictException.class)
    public ResponseEntity<String> handleCategoryExistingException(CategoryConflictException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}