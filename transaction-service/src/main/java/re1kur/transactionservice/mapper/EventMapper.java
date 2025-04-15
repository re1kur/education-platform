package re1kur.transactionservice.mapper;

import command.CreateTransactionCommand;
import event.CompleteTransactionCommand;

public interface EventMapper {
    CreateTransactionCommand createTransactionCommand(String message);

    String transactionCreatedEvent(CreateTransactionCommand command, String string);

    String transactionCreateFailedEvent(CreateTransactionCommand command);

    CompleteTransactionCommand completeTransactionCommand(String message);

    String transactionCompletedEvent(CompleteTransactionCommand command);
}
