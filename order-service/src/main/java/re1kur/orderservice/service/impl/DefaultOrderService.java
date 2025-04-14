package re1kur.orderservice.service.impl;

import event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import payload.OrderPayload;
import re1kur.orderservice.entity.Order;
import re1kur.orderservice.mapper.OrderMapper;
import re1kur.orderservice.mq.publisher.EventPublisher;
import re1kur.orderservice.repository.OrderRepository;
import re1kur.orderservice.service.OrderService;

@Service
@RequiredArgsConstructor
public class DefaultOrderService implements OrderService {
    private final OrderRepository repo;
    private final OrderMapper mapper;
    private final EventPublisher publisher;

    @Override
    public void createOrder(OrderPayload payload) {
        Order order = mapper.write(payload);
        order = repo.save(order);
        OrderCreatedEvent event = mapper.createEvent(order);
        publisher.publishEvent(event);
    }
}
