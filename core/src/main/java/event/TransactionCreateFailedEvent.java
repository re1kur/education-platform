package event;

public record TransactionCreateFailedEvent(String orderId, String userId) {
}
