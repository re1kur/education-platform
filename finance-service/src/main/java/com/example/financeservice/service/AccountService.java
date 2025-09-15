package com.example.financeservice.service;


import com.example.financeservice.entity.Account;

import java.util.UUID;

public interface AccountService {
    Account get(UUID id);

    void debit(Account account, Integer amount, UUID transactionId);

    void credit(Account account, Integer amount, UUID transactionId);

    void blockAccount(Account account, UUID transactionId);

    void releaseAccount(Account account, UUID transactionId);
}
