package com.example.financeservice.mapper;


import com.example.event.PayOrderRequest;
import com.example.financeservice.entity.Account;
import com.example.financeservice.entity.Transaction;

public interface TransactionMapper {
    Transaction create(PayOrderRequest request, Account account);

    Transaction success(Transaction transaction);

    Transaction fail(Transaction transaction);
}