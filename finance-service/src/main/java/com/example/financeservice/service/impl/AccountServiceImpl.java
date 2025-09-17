package com.example.financeservice.service.impl;

import com.example.exception.AccountConflictException;
import com.example.exception.NotEnoughBalanceOnAccountException;
import com.example.financeservice.entity.Account;
import com.example.financeservice.mapper.AccountMapper;
import com.example.financeservice.repository.AccountRepository;
import com.example.financeservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repo;
    private final AccountMapper mapper;
    private final String CONFLICT_BLOCKED_MESSAGE = "ACCOUNT [%s] ALREADY IS BLOCKED. TRANSACTION [%s] IS PENDING.";

    @Override
    public Account get(UUID userId) {
        log.info("GET OR CREATE ACCOUNT BY USER [{}].", userId);

        return repo.findByUserId(userId).orElseGet(() -> {
            log.info("ACCOUNT BY USER [{}] WAS NOT FOUND.", userId);

            Account saved = repo.save(mapper.create(userId));

            log.info("ACCOUNT [{}] BY USER [{}] IS CREATED.", saved.getId(), userId);

            return saved;
        });
    }

    @Override
    @Transactional(noRollbackFor = NotEnoughBalanceOnAccountException.class)
    public void debit(Account account, Integer amount, UUID transactionId) {
        UUID accountId = account.getId();
        log.info("DEBIT TRANSACTION [{}] FOR ACCOUNT [{}] WITH AMOUNT [{}].", transactionId, accountId, amount);

        Integer balance = account.getBalance();
        if (balance < amount) throw new NotEnoughBalanceOnAccountException(("NOT ENOUGH BALANCE ON ACCOUNT [%s]. " +
                "DEBIT AMOUNT [%s]").formatted(accountId, amount));
        balance = balance - amount;

        account.setBalance(balance);
        account.setBlocked(false);

        repo.save(account);

        log.info("DEBIT OPERATION FOR ACCOUNT [{}] ON AMOUNT [{}] IS PERFORMED SUCCESSFULLY.", accountId, amount);
    }

    @Override
    @Transactional
    public void credit(Account account, Integer amount, UUID transactionId) {
        UUID accountId = account.getId();
        log.info("CREDIT TRANSACTION [{}] FOR ACCOUNT [{}] WITH AMOUNT [{}].", transactionId, accountId, amount);

        Integer balance = account.getBalance();

        balance = balance + amount;

        account.setBalance(balance);
        account.setBlocked(false);

        repo.save(account);

        log.info("CREDIT OPERATION FOR ACCOUNT [{}] ON AMOUNT [{}] IS PERFORMED SUCCESSFULLY.", accountId, amount);
    }

    @Override
    @Transactional
    public void blockAccount(Account account, UUID transactionId) {
        UUID accountId = account.getId();
        log.info("BLOCK ACCOUNT [{}] REQUEST BY TRANSACTION [{}].", accountId, transactionId);

        int updated = repo.tryBlockAccount(accountId);

        if (updated == 0) throw new AccountConflictException(CONFLICT_BLOCKED_MESSAGE.formatted(accountId, transactionId));

        log.info("ACCOUNT [{}] IS BLOCKED FOR TRANSACTION [{}]", accountId, transactionId);
    }

    @Override
    @Transactional
    public void releaseAccount(Account account, UUID transactionId) {
        UUID accountId = account.getId();
        log.info("RELEASE ACCOUNT [{}] REQUEST BY TRANSACTION [{}].", accountId, transactionId);

        account.setBlocked(false);
        repo.save(account);

        log.info("ACCOUNT [{}] IS RELEASED FOR TRANSACTION [{}]", accountId, transactionId);
    }
}