package re1kur.catalogueservice.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import re1kur.catalogueservice.exception.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class DefaultControllerAdvice {

    @ExceptionHandler(GoodsNotFoundException.class)
    public ResponseEntity<String> handleGoodsNotFoundException(GoodsNotFoundException ex) {
        return ResponseEntity.status(NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<String> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        return ResponseEntity.status(NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(GoodsExistingException.class)
    public ResponseEntity<String> handleGoodsExistingException(GoodsExistingException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(CatalogueGoodsExistingException.class)
    public ResponseEntity<String> handleCatalogueGoodsExistingException(CatalogueGoodsExistingException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<String> handleCategoryExistingException(CategoryAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}