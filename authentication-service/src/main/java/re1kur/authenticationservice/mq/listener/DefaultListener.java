package re1kur.authenticationservice.mq.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import re1kur.authenticationservice.entity.User;
import re1kur.authenticationservice.repository.UserRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultListener {
    private final UserRepository repo;

    @RabbitListener(queues = "${custom.message-broker.listen-queues.user-verification.name}")
    public void listenVerificationUserQueue(String email) {
        log.info("Listened verification user queue {}", email);
        User user = repo.findByEmail(email).get();
        user.setIsEmailVerified(true);
        repo.save(user);
        log.info("User {} is verified", email);
    }
}
