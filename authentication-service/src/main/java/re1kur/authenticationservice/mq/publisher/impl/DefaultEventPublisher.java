package re1kur.authenticationservice.mq.publisher.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import re1kur.authenticationservice.mq.publisher.EventPublisher;

@Service
@RequiredArgsConstructor
public class DefaultEventPublisher implements EventPublisher {
    private final RabbitTemplate template;

    @Value("${custom.rabbitmq.publish-queues.user-registration.routing-key}")
    private String userRegistrationRoutingKey;

    @Value("${custom.rabbitmq.exchange}")
    private String exchange;

    @Override
    @SneakyThrows
    public void publishUserRegistrationEvent(String email) {
        template.convertAndSend(exchange, userRegistrationRoutingKey, email);
    }
}
