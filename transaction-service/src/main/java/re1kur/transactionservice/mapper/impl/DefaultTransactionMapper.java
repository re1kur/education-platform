package re1kur.transactionservice.mapper.impl;

import command.CreateTransactionCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import dto.TransactionDto;
import re1kur.transactionservice.entity.Transaction;
import re1kur.transactionservice.mapper.TransactionMapper;
import re1kur.transactionservice.repository.TransactionTypeRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DefaultTransactionMapper implements TransactionMapper {
    private final TransactionTypeRepository typeRepo;

    @Override
    public TransactionDto read(Transaction transaction) {
        return new TransactionDto(
                transaction.getId().toString(),
                transaction.getUserId().toString(),
                transaction.getOrderId().toString(),
                transaction.getType().getName(),
                transaction.getStatus().getName(),
                transaction.getAmount(),
                transaction.getDate(),
                transaction.getDescription()
        );
    }

    @Override
    public Transaction create(CreateTransactionCommand command) {
        return Transaction.builder()
                .userId(UUID.fromString(command.userId()))
                .orderId(UUID.fromString(command.orderId()))
                .amount(command.amount())
                .type(typeRepo.findByName(command.transactionType()))
                .build();
    }
}
