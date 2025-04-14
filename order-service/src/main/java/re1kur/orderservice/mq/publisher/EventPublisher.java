package re1kur.orderservice.mq.publisher;

import event.OrderCreatedEvent;

public interface EventPublisher {
    void publishEvent(OrderCreatedEvent event);
}
