package event;

import java.math.BigDecimal;

public record UserBalanceBlockedEvent(
        String orderId,
        String userId,
        BigDecimal amount,
        String transactionType
) {
}
