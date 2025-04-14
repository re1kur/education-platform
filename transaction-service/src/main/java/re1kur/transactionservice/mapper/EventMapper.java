package re1kur.transactionservice.mapper;

import command.CreateTransactionCommand;

public interface EventMapper {
    CreateTransactionCommand createTransactionCommand(String message);

    String transactionCreatedEvent(CreateTransactionCommand command, String string);
}
