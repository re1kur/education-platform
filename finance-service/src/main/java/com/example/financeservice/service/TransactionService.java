package com.example.financeservice.service;

import com.example.event.PayOrderRequest;
import com.example.financeservice.entity.Transaction;

import java.util.UUID;

public interface TransactionService {

    Transaction create(PayOrderRequest event);

    void perform(UUID transactionId);
}
