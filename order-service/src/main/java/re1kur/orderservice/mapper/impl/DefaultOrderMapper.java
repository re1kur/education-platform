package re1kur.orderservice.mapper.impl;

import event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import payload.OrderPayload;
import re1kur.orderservice.entity.Order;
import re1kur.orderservice.mapper.OrderMapper;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DefaultOrderMapper implements OrderMapper {

    @Override
    public Order write(OrderPayload payload) {
        return Order.builder()
                .userId(UUID.fromString(payload.userId()))
                .goodsId(payload.goodsId())
                .build();
    }

    @Override
    public OrderCreatedEvent createEvent(Order order) {
        return new OrderCreatedEvent(
                order.getId().toString(),
                order.getUserId().toString(),
                order.getGoodsId());
    }
}
