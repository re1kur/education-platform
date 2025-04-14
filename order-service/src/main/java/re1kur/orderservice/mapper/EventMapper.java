package re1kur.orderservice.mapper;

import event.OrderCreatedEvent;

public interface EventMapper {
    String message(OrderCreatedEvent event);

}
