package event;

public record OrderCreatedEvent (
        String orderId,
        String userId,
        Integer goodsId) {
}
