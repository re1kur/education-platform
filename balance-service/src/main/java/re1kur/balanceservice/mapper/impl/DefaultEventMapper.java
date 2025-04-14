package re1kur.balanceservice.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import command.BlockUserBalanceCommand;
import event.UserBalanceBlockFailedEvent;
import event.UserBalanceBlockedEvent;
import event.UserRegistrationEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import re1kur.balanceservice.mapper.EventMapper;

@Component
@RequiredArgsConstructor
public class DefaultEventMapper implements EventMapper {
    private final ObjectMapper serializer;

    @SneakyThrows
    @Override
    public BlockUserBalanceCommand blockUserBalanceCommand(String message) {
        return serializer.readValue(message, BlockUserBalanceCommand.class);
    }

    @SneakyThrows
    @Override
    public UserRegistrationEvent userRegistrationEvent(String message) {
        return serializer.readValue(message, UserRegistrationEvent.class);
    }

    @SneakyThrows
    @Override
    public String userBalanceBlockedEvent(BlockUserBalanceCommand command) {
        UserBalanceBlockedEvent event = new UserBalanceBlockedEvent(
                command.orderId(),
                command.userId(),
                command.price(),
                command.transactionType());
        return serializer.writeValueAsString(event);
    }

    @SneakyThrows
    @Override
    public String userBalanceBlockFailedEvent(BlockUserBalanceCommand command) {
        UserBalanceBlockFailedEvent event = new UserBalanceBlockFailedEvent(command.orderId());
        return serializer.writeValueAsString(event);
    }
}
