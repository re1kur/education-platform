package com.example.balanceservice.service.impl;

import com.example.balanceservice.repository.AccountRepository;
import com.example.dto.BalanceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import re1kur.balanceservice.service.BalanceService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultBalanceService implements BalanceService {
    private final AccountRepository repo;

    @Override
    public BalanceDto getBalance(String userId) {
//        if (userId == null || userId.isEmpty()) throw new NoSubjectClaimException("No user ID found");
//        re1kur.balanceservice.entity.Account account = repo.findById(UUID.fromString(userId)).orElseThrow(() -> new UserNotFoundException("User not found"));
//        return new BalanceDto(account.getUserId().toString(), account.getBalance());
        return new BalanceDto(null, null);
    }
}