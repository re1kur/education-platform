package re1kur.transactionservice.mapper;


import command.CreateTransactionCommand;
import dto.TransactionDto;
import exception.StatusNotFoundException;
import exception.TransactionTypeNotFoundException;
import re1kur.transactionservice.entity.Transaction;

public interface TransactionMapper {
    TransactionDto read(Transaction transaction);

    Transaction create(CreateTransactionCommand command) throws TransactionTypeNotFoundException;

    Transaction complete(Transaction transaction) throws TransactionTypeNotFoundException, StatusNotFoundException;
}
