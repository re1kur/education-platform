package re1kur.authenticationservice.mq.listener;

import exception.UserVerificationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import re1kur.authenticationservice.service.AuthenticationService;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultListener {
    private final AuthenticationService service;

    @RabbitListener(queues = "${custom.message-broker.listen-queues.user-verification.name}")
    public void listenVerificationUserQueue(String email) throws UserVerificationException {
        log.info("Listened verification user queue {}", email);
        service.verify(email);
        log.info("User {} is verified", email);
    }
}
