package com.example.financeservice.mapper.impl;

import com.example.financeservice.entity.Account;
import com.example.financeservice.mapper.AccountMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AccountMapperImpl implements AccountMapper {
    @Override
    public Account create(UUID userId) {
        return Account.builder()
                .userId(userId)
                .build();
    }
}
