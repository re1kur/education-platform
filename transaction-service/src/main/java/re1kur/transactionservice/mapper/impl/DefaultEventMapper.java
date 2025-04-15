package re1kur.transactionservice.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import command.CreateTransactionCommand;
import event.CompleteTransactionCommand;
import event.TransactionCompletedEvent;
import event.TransactionCreateFailedEvent;
import event.TransactionCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import re1kur.transactionservice.mapper.EventMapper;

@Component
@RequiredArgsConstructor
public class DefaultEventMapper implements EventMapper {
    private final ObjectMapper serializer;


    @SneakyThrows
    @Override
    public CreateTransactionCommand createTransactionCommand(String message) {
        return serializer.readValue(message, CreateTransactionCommand.class);
    }

    @SneakyThrows
    @Override
    public String transactionCreatedEvent(CreateTransactionCommand command, String transactionId) {
        TransactionCreatedEvent event = new TransactionCreatedEvent(
                command.orderId(),
                command.userId(),
                transactionId,
                command.transactionType(),
                command.amount());
        return serializer.writeValueAsString(event);
    }

    @SneakyThrows
    @Override
    public String transactionCreateFailedEvent(CreateTransactionCommand command) {
        TransactionCreateFailedEvent event = new TransactionCreateFailedEvent(
                command.orderId(),
                command.userId()
        );

        return serializer.writeValueAsString(event);
    }

    @SneakyThrows
    @Override
    public CompleteTransactionCommand completeTransactionCommand(String message) {
        return serializer.readValue(message, CompleteTransactionCommand.class);
    }

    @SneakyThrows
    @Override
    public String transactionCompletedEvent(CompleteTransactionCommand command) {
        TransactionCompletedEvent event = new TransactionCompletedEvent(
            command.orderId()
        );
        return serializer.writeValueAsString(event);
    }
}
