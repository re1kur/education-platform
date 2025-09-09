package com.example.fileservice.controller;

import com.example.exception.FileNotFoundException;
import com.example.exception.FileUpdateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Map<String, Object> body =  Map.of("status", status.value(), "message", ex.getMessage());

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(FileUpdateException.class)
    public ResponseEntity<?> handleFileUpdateException(FileUpdateException ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Map<String, Object> body =  Map.of("status", status.value(), "message", ex.getMessage());

        return ResponseEntity.internalServerError().body(body);
    }
}
