package com.example.financeservice.mapper.impl;

import com.example.enums.TransactionReferenceType;
import com.example.enums.TransactionType;
import com.example.event.PayOrderRequest;
import com.example.financeservice.entity.Account;
import com.example.financeservice.entity.Transaction;
import com.example.financeservice.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
}
