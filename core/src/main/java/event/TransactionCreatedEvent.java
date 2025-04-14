package event;

public record TransactionCreatedEvent (
        String orderId,
        String userId,
        String transactionId
) {
}
