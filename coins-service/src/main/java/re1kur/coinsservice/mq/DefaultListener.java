package re1kur.coinsservice.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import re1kur.coinsservice.entity.Balance;
import re1kur.coinsservice.mq.event.UserRegistrationEvent;
import re1kur.coinsservice.repository.BalanceRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultListener {
    private final BalanceRepository balanceRepo;
    private final ObjectMapper serializer;

    @RabbitListener(queues = "${custom.message-broker.listen-queues.user-registration.name}")
    @SneakyThrows
    public void listenUserRegistrationQueue(String message) {
        log.info("Listening user registration queue: {}", message);
        UserRegistrationEvent event = serializer.readValue(message, UserRegistrationEvent.class);
        Balance balance = Balance.builder()
                .userId(UUID.fromString(event.id()))
                .build();
        balanceRepo.save(balance);
    }
}
