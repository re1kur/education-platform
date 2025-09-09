package com.example.balanceservice.service;

import dto.TransactionDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TransactionService {
    ResponseEntity<List<TransactionDto>> getHistory(String subject);
}
