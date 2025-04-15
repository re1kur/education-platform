package re1kur.balanceservice.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import command.BlockUserBalanceCommand;
import command.ProcessUserBalanceCommand;
import command.UnblockUserBalanceCommand;
import event.*;
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

    @SneakyThrows
    @Override
    public UnblockUserBalanceCommand unblockUserBalanceCommand(String message) {
        return serializer.readValue(message, UnblockUserBalanceCommand.class);
    }

    @SneakyThrows
    @Override
    public String userBalanceUnblockedEvent(UnblockUserBalanceCommand command) {
        UserBalanceUnblockedEvent event = new UserBalanceUnblockedEvent(
                command.orderId()
        );
        return serializer.writeValueAsString(event);
    }

    @SneakyThrows
    @Override
    public ProcessUserBalanceCommand processUserBalanceCommand(String message) {
        return serializer.readValue(message, ProcessUserBalanceCommand.class);
    }

    @SneakyThrows
    @Override
    public String userBalanceProcessedEvent(ProcessUserBalanceCommand command) {
        UserBalanceProcessedEvent event = new UserBalanceProcessedEvent(
                command.orderId(),
                command.transactionId()
        );
        return serializer.writeValueAsString(event);
    }
}
