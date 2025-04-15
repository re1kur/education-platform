package re1kur.authenticationservice.mq.publisher.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import event.UserRegistrationEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import re1kur.authenticationservice.entity.User;
import re1kur.authenticationservice.mq.publisher.EventPublisher;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultEventPublisher implements EventPublisher {
    private final RabbitTemplate template;
    private final ObjectMapper serializer;

    @Value("${custom.message-broker.publish-queues.user-registration.routing-key}")
    private String userRegistrationRoutingKey;

    @Value("${custom.message-broker.exchange}")
    private String exchange;

    @Override
    @SneakyThrows
    public void publishUserRegistrationEvent(User user) {
        UserRegistrationEvent event = new UserRegistrationEvent(user.getId().toString(), user.getEmail());
        log.info("Publishing user registration event: {}", event);
        template.convertAndSend(exchange, userRegistrationRoutingKey, serializer.writeValueAsString(event));
    }
}
