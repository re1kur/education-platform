package com.example.financeservice.mapper;


import com.example.dto.PageDto;
import com.example.dto.TransactionDto;
import com.example.event.PayOrderRequest;
import com.example.financeservice.entity.Account;
import com.example.financeservice.entity.Transaction;
import org.springframework.data.domain.Page;

public interface TransactionMapper {
    Transaction create(PayOrderRequest request, Account account);

    Transaction success(Transaction transaction);

    Transaction fail(Transaction transaction);

    PageDto<TransactionDto> readPage(Page<Transaction> page);

    TransactionDto read(Transaction transaction);
}