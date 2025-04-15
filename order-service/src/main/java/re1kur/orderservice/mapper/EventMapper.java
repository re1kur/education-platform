package re1kur.orderservice.mapper;

import event.OrderCreatedEvent;

public interface EventMapper {
    String message(OrderCreatedEvent event);

    String rejectOrderCommand(String message);

    String approveOrderCommand(String message);
}
