package event;

public record UserBalanceProcessedEvent (String orderId, String transactionId) {
}
