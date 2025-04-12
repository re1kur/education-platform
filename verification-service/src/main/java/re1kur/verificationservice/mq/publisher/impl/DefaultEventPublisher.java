package re1kur.verificationservice.mq.publisher.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import re1kur.verificationservice.entity.Code;
import re1kur.verificationservice.mq.event.VerificationCodeGenerationEvent;
import re1kur.verificationservice.mq.publisher.EventPublisher;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultEventPublisher implements EventPublisher {
    private final RabbitTemplate template;
    private final ObjectMapper serializer;

    @Value("${custom.message-broker.publish-queues.code-generation.routing-key}")
    private String codeGenerationRoutingKey;
    @Value("${custom.message-broker.publish-queues.verification.routing-key}")
    private String userVerificationRoutingKey;
    @Value("${custom.message-broker.exchange}")
    private String exchange;

    @Override
    public void publishGenerationCodeEvent(Code code) throws JsonProcessingException {
        VerificationCodeGenerationEvent event = new VerificationCodeGenerationEvent(code.getEmail(), code.getContent());
        template.convertAndSend(exchange, codeGenerationRoutingKey, serializer.writeValueAsString(event));
        log.info("Published generation code event: {}", code);
    }

    @Override
    public void publishUserVerificationEvent(String email) {
        template.convertAndSend(exchange, userVerificationRoutingKey, email);
        log.info("Published user verification event: {}", email);
    }
}
