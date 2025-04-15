package re1kur.balanceservice.mapper;

import command.BlockUserBalanceCommand;
import command.ProcessUserBalanceCommand;
import command.UnblockUserBalanceCommand;
import event.UserRegistrationEvent;

public interface EventMapper {
    BlockUserBalanceCommand blockUserBalanceCommand(String message);

    UserRegistrationEvent userRegistrationEvent(String message);

    String userBalanceBlockedEvent(BlockUserBalanceCommand command);

    String userBalanceBlockFailedEvent(BlockUserBalanceCommand command);

    UnblockUserBalanceCommand unblockUserBalanceCommand(String message);

    String userBalanceUnblockedEvent(UnblockUserBalanceCommand message);

    ProcessUserBalanceCommand processUserBalanceCommand(String message);

    String userBalanceProcessedEvent(ProcessUserBalanceCommand command);
}
