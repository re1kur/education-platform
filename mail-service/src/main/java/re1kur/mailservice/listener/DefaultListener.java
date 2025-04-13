package re1kur.mailservice.listener;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import re1kur.mailservice.service.MailService;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultListener {
    private final MailService service;

    @RabbitListener(queues = "${custom.rabbitmq.listen-queues.user-welcome.name}")
    @SneakyThrows
    public void listenUserRegistration(String message) {
        log.info("Listening user registration: {}", message);
        service.sendWelcomeMail(message);
    }

    @RabbitListener(queues = "${custom.rabbitmq.listen-queues.code-generation.name}")
    public void listenVerificationCodeGeneration(String message) {
        log.info("Listening verification code generation: {}", message);
        service.sendGenerationCode(message);
    }
}
