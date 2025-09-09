package com.example.balanceservice.mapper.impl;

//import command.CreateTransactionCommand;
//import dto.TransactionDto;
//import exception.StatusNotFoundException;
//import exception.TransactionTypeNotFoundException;
import com.example.balanceservice.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DefaultTransactionMapper implements TransactionMapper {
//    private final TransactionTypeRepository typeRepo;
//    private final StatusRepository statusRepo;
//
//    @Override
//    public TransactionDto read(Transaction transaction) {
//        return new TransactionDto(
//                transaction.getId().toString(),
//                transaction.getUserId().toString(),
//                transaction.getOrderId().toString(),
//                transaction.getType().getName(),
//                transaction.getStatus().getName(),
//                transaction.getAmount(),
//                transaction.getDate(),
//                transaction.getDescription()
//        );
//    }
//
//    @Override
//    public Transaction create(CreateTransactionCommand command) throws TransactionTypeNotFoundException {
//        return Transaction.builder()
//                .userId(UUID.fromString(command.userId()))
//                .orderId(UUID.fromString(command.orderId()))
//                .amount(command.amount())
//                .type(typeRepo.findByName(command.transactionType())
//                        .orElseThrow(() -> new TransactionTypeNotFoundException("Transaction type %s does not exist.".formatted(command.transactionType()))))
//                .build();
//    }
//
//    @Override
//    public Transaction complete(Transaction transaction) throws StatusNotFoundException {
//        Status approved = statusRepo.findByName("APPROVED")
//                .orElseThrow(() -> new StatusNotFoundException("Status \"APPROVED\" does not exist."));
//        transaction.setStatus(approved);
//        return transaction;
//    }
}
