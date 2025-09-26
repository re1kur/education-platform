package com.example.financeservice.service;

import com.example.dto.PageDto;
import com.example.dto.TransactionDto;
import com.example.event.PayOrderRequest;
import com.example.financeservice.entity.Transaction;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

public interface TransactionService {

    Transaction create(PayOrderRequest event);

    void perform(UUID transactionId);

    PageDto<TransactionDto> readAll(Jwt jwt, int page, int size);

    TransactionDto read(Jwt jwt, UUID id);
}
