package re1kur.transactionservice.mq;

import command.CreateTransactionCommand;
import event.CompleteTransactionCommand;
import exception.TransactionNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import re1kur.transactionservice.entity.Transaction;
import re1kur.transactionservice.mapper.EventMapper;
import re1kur.transactionservice.mapper.TransactionMapper;
import re1kur.transactionservice.repository.TransactionRepository;

import java.util.UUID;


@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultListener {
    private final TransactionRepository repo;
    private final EventMapper eventMapper;
    private final TransactionMapper mapper;
    private final RabbitTemplate template;

    @Value("${custom.message-broker.exchange}")
    private String exchange;

    @Value("${custom.message-broker.publish-queues.transaction-created.routing-key}")
    private String transactionCreatedQueueRoutingKey;

    @Value("${custom.message-broker.publish-queues.transaction-create-failed.routing-key}")
    private String transactionCreateFailedQueueRoutingKey;

    @Value("${custom.message-broker.publish-queues.transaction-completed.routing-key}")
    private String transactionCompletedQueueRoutingKey;

    @Value("${custom.message-broker.publish-queues.transaction-complete-failed.routing-key}")
    private String transactionCompleteFailedQueueRoutingKey;

    @RabbitListener(queues = "${custom.message-broker.listen-queues.create-transaction-command.name}")
    @Transactional
    public void listenCreateTransactionCommand(String message) {
        log.info("Listening create transaction command: {}", message);
        CreateTransactionCommand command = eventMapper.createTransactionCommand(message);
        try {
            Transaction transaction = mapper.create(command);
            transaction = repo.save(transaction);

            String json = eventMapper.transactionCreatedEvent(command, transaction.getId().toString());

            template.convertAndSend(exchange, transactionCreatedQueueRoutingKey, json);
            log.info("Created transaction: {}", json);
        } catch (Exception e) {
            log.error("Create transaction failed: {}", e.getMessage());
            String json = eventMapper.transactionCreateFailedEvent(command);

            template.convertAndSend(exchange, transactionCreateFailedQueueRoutingKey, json);
        }
    }

    @RabbitListener(queues = "${custom.message-broker.listen-queues.complete-transaction-command.name}")
    @Transactional
    public void listenCompleteTransactionCommand(String message) {
        log.info("Listening complete transaction command: {}", message);
        CompleteTransactionCommand command = eventMapper.completeTransactionCommand(message);
        try {
            Transaction transaction = repo.findById(UUID.fromString(command.transactionId()))
                    .orElseThrow(() -> new TransactionNotFoundException(
                            "Transaction with id '%s' does not exist.".formatted(command.transactionId())));

            transaction = mapper.complete(transaction);

            repo.save(transaction);

            String json = eventMapper.transactionCompletedEvent(command);

            template.convertAndSend(exchange, transactionCompletedQueueRoutingKey, json);
            log.info("Completed transaction: {}", transaction);
        } catch (Exception e) {
            log.error("Complete transaction failed: {}", e.getMessage());
//            String json = eventMapper.transactionCompleteFailedEvent(command);
//
//            template.convertAndSend(exchange, transactionCompleteFailedQueueRoutingKey, json);
        }
    }
}
