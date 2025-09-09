package com.example.balanceservice.mapper;

import event.OrderCreatedEvent;
import payload.OrderPayload;
import re1kur.orderservice.entity.Order;

public interface OrderMapper {
    Order write(OrderPayload payload);

    OrderCreatedEvent createEvent(Order order);
}
