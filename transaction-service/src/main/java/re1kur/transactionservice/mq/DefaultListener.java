package re1kur.transactionservice.mq;

import command.CreateTransactionCommand;
import exception.TransactionTypeNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import re1kur.transactionservice.entity.Transaction;
import re1kur.transactionservice.mapper.EventMapper;
import re1kur.transactionservice.mapper.TransactionMapper;
import re1kur.transactionservice.repository.TransactionRepository;


@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultListener {
    private final TransactionRepository repo;
    private final EventMapper eventMapper;
    private final TransactionMapper mapper;
    private final RabbitTemplate template;

    @RabbitListener(queues = "${custom.message-broker.listen-queues.create-transaction-command.name}")
    @Transactional
    public void listenCreateTransactionCommand(String message) throws TransactionTypeNotFoundException {
        log.info("Listening create transaction command: {}", message);
        CreateTransactionCommand command = eventMapper.createTransactionCommand(message);
        Transaction transaction = mapper.create(command);
        transaction = repo.save(transaction);

        String json = eventMapper.transactionCreatedEvent(command, transaction.getId().toString());
        log.info("Created transaction: {}", json);
    }
}
