package com.example.financeservice.mapper;

import com.example.financeservice.entity.Account;

import java.util.UUID;

public interface AccountMapper {
    Account create(UUID userId);
}
