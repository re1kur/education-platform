package re1kur.mailservice.mq.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import re1kur.mailservice.service.MailService;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultListener {
    private final MailService service;

    @RabbitListener(queues = "${custom.rabbitmq.listen-queues.user-registration.name}")
    public void listenUserRegistration(String email) {
        log.info("Listening user registration: {}", email);
        service.sendWelcomeMail(email);
    }

    @RabbitListener(queues = "${custom.rabbitmq.listen-queues.code-generation.name}")
    public void listenVerificationCodeGeneration(String message) {
        log.info("Listening verification code generation: {}", message);
        service.sendGenerationCode(message);
    }
}
