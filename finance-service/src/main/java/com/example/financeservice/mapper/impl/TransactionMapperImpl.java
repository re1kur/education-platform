package com.example.financeservice.mapper.impl;

import com.example.dto.PageDto;
import com.example.dto.TransactionDto;
import com.example.enums.TransactionReferenceType;
import com.example.enums.TransactionStatus;
import com.example.enums.TransactionType;
import com.example.event.PayOrderRequest;
import com.example.financeservice.entity.Account;
import com.example.financeservice.entity.Transaction;
import com.example.financeservice.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public Transaction create(PayOrderRequest request, Account account) {
        return Transaction.builder()
                .referenceType(TransactionReferenceType.ORDER)
                .referenceId(request.orderId())
                .type(TransactionType.DEBIT)
                .amount(request.amount())
                .account(account)
                .build();
    }

    @Override
    public Transaction success(Transaction transaction) {
        transaction.setExecutedAt(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.SUCCESS);

        return transaction;
    }

    @Override
    public Transaction fail(Transaction transaction) {
        transaction.setExecutedAt(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.FAIL);

        return transaction;
    }

    @Override
    public PageDto<TransactionDto> readPage(Page<Transaction> page) {
        return new PageDto<>(
                page.stream().map(this::read).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.hasNext(),
                page.hasPrevious());
    }

    @Override
    public TransactionDto read(Transaction transaction) {
        return TransactionDto.builder()
                .id(transaction.getId())
                .createdAt(transaction.getCreatedAt())
                .executedAt(transaction.getExecutedAt())
                .accountId(transaction.getAccount().getId())
                .status(transaction.getStatus().name())
                .amount(transaction.getAmount())
                .type(transaction.getType().name())
                .referenceType(transaction.getReferenceType().name())
                .referenceId(transaction.getReferenceId())
                .build();
    }
}
