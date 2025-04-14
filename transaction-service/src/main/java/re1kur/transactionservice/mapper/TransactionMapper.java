package re1kur.transactionservice.mapper;


import command.CreateTransactionCommand;
import dto.TransactionDto;
import re1kur.transactionservice.entity.Transaction;

public interface TransactionMapper {
    TransactionDto read(Transaction transaction);

    Transaction create(CreateTransactionCommand command);
}
