package re1kur.transactionservice.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import command.CreateTransactionCommand;
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
        TransactionCreatedEvent event = new TransactionCreatedEvent(command.orderId(), command.userId(), transactionId);
        return serializer.writeValueAsString(event);
    }
}
