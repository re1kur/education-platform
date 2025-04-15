package event;

public record CompleteTransactionCommand (String orderId, String transactionId) {
}
