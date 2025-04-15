package re1kur.orderservice.mq.listener;

import exception.OrderNotFoundException;
import exception.StatusNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import re1kur.orderservice.entity.Order;
import re1kur.orderservice.entity.Status;
import re1kur.orderservice.mapper.EventMapper;
import re1kur.orderservice.repository.OrderRepository;
import re1kur.orderservice.repository.StatusRepository;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultListener {
    private final EventMapper mapper;
    private final OrderRepository repo;
    private final StatusRepository statusRepo;

    @RabbitListener(queues = "${custom.message-broker.listen-queues.reject-order-command.name}")
    @Transactional
    public void listenRejectOrderCommand(String message) throws OrderNotFoundException, StatusNotFoundException {
        log.info("Listening reject order by command: {}.", message);
        String orderId = mapper.rejectOrderCommand(message);
        Order order = repo.findById(UUID.fromString(orderId)).orElseThrow(() -> new OrderNotFoundException("Order with id '%s' does not exist.".formatted(orderId)));
        Status rejectStatus = statusRepo.findByName("REJECTED").orElseThrow(() -> new StatusNotFoundException("Status 'REJECT' does not exists."));

        order.setStatus(rejectStatus);
        repo.save(order);
        log.info("Order rejected: {}.", order.toString());
    }
}
