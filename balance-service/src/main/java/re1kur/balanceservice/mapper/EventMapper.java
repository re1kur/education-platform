package re1kur.balanceservice.mapper;

import command.BlockUserBalanceCommand;
import event.UserRegistrationEvent;

public interface EventMapper {
    BlockUserBalanceCommand blockUserBalanceCommand(String message);

    UserRegistrationEvent userRegistrationEvent(String message);

    String userBalanceBlockedEvent(BlockUserBalanceCommand command);

    String userBalanceBlockFailedEvent(BlockUserBalanceCommand command);
}
