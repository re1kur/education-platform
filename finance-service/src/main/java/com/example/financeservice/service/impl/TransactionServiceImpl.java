package com.example.financeservice.service.impl;

import com.example.enums.OutboxType;
import com.example.enums.TransactionType;
import com.example.event.PayOrderRequest;
import com.example.exception.NotEnoughBalanceOnAccountException;
import com.example.exception.TransactionNotFoundException;
import com.example.financeservice.entity.Account;
import com.example.financeservice.entity.Transaction;
import com.example.financeservice.mapper.TransactionMapper;
import com.example.financeservice.repository.TransactionRepository;
import com.example.financeservice.service.AccountService;
import com.example.financeservice.service.TransactionService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository repo;
    private final TransactionMapper mapper;
    private final AccountService accountService;


    private final String NOT_FOUND_MESSAGE = "TRANSACTION [%s] WAS NOT FOUND.";
    private final String UKNOWN_TYPE_MESSAGE = "TRANSACTION [%s] HAS UNKNOWN TYPE [%s].";
    private final String NOT_ENOUGH_MONEY_MESSAGE = "NOT ENOUGH BALANCE FOR TRANSACTION [{}]. RELEASING ACCOUNT [{}].";

    @PostConstruct
    public void postConstruct() {
        log.info("POST CONSTRUCT service.");
    }

    @Override
    @Transactional
    public Transaction create(PayOrderRequest request) {
        log.info("CREATE TRANSACTION [{}] REQUEST.", OutboxType.PAY_ORDER_REQUEST);

        Account account = accountService.get(request.userId());
        Transaction mapped = mapper.create(request, account);

        Transaction saved = repo.save(mapped);

        log.info("TRANSACTION [{}] IS CREATED.", saved.getId());
        return saved;
    }

    @Override
    @Transactional
    public void perform(UUID transactionId) {
        log.info("PERFORM TRANSACTION [{}] REQUEST.", transactionId);

        Transaction found = repo.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException(NOT_FOUND_MESSAGE.formatted(transactionId)));

        Account account = found.getAccount();
        Integer amount = found.getAmount();
        TransactionType type = found.getType();

        accountService.blockAccount(account, transactionId);

        try {
            if (type == TransactionType.DEBIT) {
                accountService.debit(account, amount, transactionId);
            } else if (type == TransactionType.CREDIT) {
                accountService.credit(account, amount, transactionId);
            } else {
                throw new IllegalStateException(UKNOWN_TYPE_MESSAGE.formatted(transactionId, type));
            }
        } catch (NotEnoughBalanceOnAccountException e) {
            log.warn(NOT_ENOUGH_MONEY_MESSAGE,
                    transactionId, account.getId(), e);
            throw e;
        } catch (RuntimeException e) {
            log.error("UNEXPECTED ERROR DURING TRANSACTION [{}]. RELEASING ACCOUNT [{}].",
                    transactionId, account.getId(), e);
            throw e;
        } finally {
            accountService.releaseAccount(account, transactionId);
        }

        log.info("TRANSACTION [{}] IS PERFORMED SUCCESSFULLY.", transactionId);
    }
}
