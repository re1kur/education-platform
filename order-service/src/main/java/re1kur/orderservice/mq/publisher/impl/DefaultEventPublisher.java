package re1kur.orderservice.mq.publisher.impl;

import event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import re1kur.orderservice.mapper.EventMapper;
import re1kur.orderservice.mq.publisher.EventPublisher;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultEventPublisher implements EventPublisher {
    private final RabbitTemplate template;
    private final EventMapper mapper;

    @Value("${custom.message-broker.exchange}")
    private String exchange;

    @Value("${custom.message-broker.publish-queues.order-created.routing-key}")
    private String orderCreatedRoutingKey;

    @Override
    public void publishEvent(OrderCreatedEvent event) {
        log.info("Publishing order created event {}", event);
        String json = mapper.message(event);
        template.convertAndSend(exchange, orderCreatedRoutingKey, json);
    }
}
