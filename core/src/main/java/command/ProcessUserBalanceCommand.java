package command;

import java.math.BigDecimal;

public record ProcessUserBalanceCommand(
        String orderId,
        String userId,
        String transactionId,
        String transactionType,
        BigDecimal amount
) {
}
