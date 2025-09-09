//package re1kur.balanceservice.mq;
//
//import command.BlockUserBalanceCommand;
//import command.ProcessUserBalanceCommand;
//import command.UnblockUserBalanceCommand;
//import event.UserRegistrationEvent;
//import exception.BalanceAlreadyBlockedException;
//import exception.NotEnoughCoinsOnBalanceException;
//import exception.UserNotFoundException;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import re1kur.balanceservice.entity.Balance;
//import re1kur.balanceservice.mapper.EventMapper;
//import re1kur.balanceservice.repository.BalanceRepository;
//
//import java.math.BigDecimal;
//import java.util.UUID;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class DefaultListener {
//    private final BalanceRepository repo;
//    private final EventMapper mapper;
//    private final RabbitTemplate template;
//
//    @Value("${custom.message-broker.exchange}")
//    private String exchange;
//
//    @Value("${custom.message-broker.publish-queues.user-balance-blocked-queue.routing-key}")
//    private String userBalanceBlockedRoutingKey;
//
//    @Value("${custom.message-broker.publish-queues.user-balance-block-failed-queue.routing-key}")
//    private String userBalanceBlockFailedRoutingKey;
//
//    @Value("${custom.message-broker.publish-queues.user-balance-unblocked-queue.routing-key}")
//    private String userBalanceUnblockedRoutingKey;
//
//    @Value("${custom.message-broker.publish-queues.user-balance-processed-queue.routing-key}")
//    private String userBalanceProcessedRoutingKey;
//
//    @RabbitListener(queues = "${custom.message-broker.listen-queues.user-registration-balance.name}")
//    @Transactional
//    public void listenUserRegistrationQueue(String message) {
//        log.info("Listening user registration queue: {}", message);
//        UserRegistrationEvent event = mapper.userRegistrationEvent(message);
//        Balance balance = Balance.builder()
//                .userId(UUID.fromString(event.id()))
//                .build();
//        repo.save(balance);
//    }
//
//    @RabbitListener(queues = "${custom.message-broker.listen-queues.block-user-balance-command.name}")
//    @Transactional
//    public void listenBlockUserBalanceCommand(String message) {
//        log.info("Listening block user balance command {}", message);
//        BlockUserBalanceCommand command = mapper.blockUserBalanceCommand(message);
//        try {
//            Balance balance = repo.findById(UUID.fromString(command.userId()))
//                    .orElseThrow(() -> new UserNotFoundException("User with id %s does not exist.".formatted(command.userId())));
//            if (balance.getIsBlocked()) {
//                log.info("Balance of user with id '{}' already blocked.", command.userId());
//                throw new BalanceAlreadyBlockedException("Balance of user with id '%s' already blocked.".formatted(command.userId()));
//            }
//            else {
//                if (command.transactionType().equals("DEBIT")) checkIsEnoughCoins(balance.getBalance(), command.price());
//                balance.setIsBlocked(true);
//                repo.save(balance);
//                log.info("Balance of user with id '{}' blocked.", command.userId());
//                String json = mapper.userBalanceBlockedEvent(command);
//
//                template.convertAndSend(exchange, userBalanceBlockedRoutingKey, json);
//            }
//        } catch (Exception e) {
//            log.error("Error while listening block user balance command:\n{}", e.getMessage());
//            String json = mapper.userBalanceBlockFailedEvent(command);
//
//            template.convertAndSend(exchange, userBalanceBlockFailedRoutingKey, json);
//        }
//    }
//
//    @RabbitListener(queues = "${custom.message-broker.listen-queues.unblock-user-balance-command.name}")
//    @Transactional
//    public void listenUnblockUserBalanceCommand(String message) throws UserNotFoundException {
//        log.info("Listening unblock user balance command: {}", message);
//        UnblockUserBalanceCommand command = mapper.unblockUserBalanceCommand(message);
//
//        Balance balance = repo.findById(UUID.fromString(command.userId()))
//                .orElseThrow(() -> new UserNotFoundException("User with id %s does not exist.".formatted(command.userId())));
//        balance.setIsBlocked(false);
//        repo.save(balance);
//
//        log.info("Balance of user with id '{}' unblocked.", command.userId());
//
//        String json = mapper.userBalanceUnblockedEvent(command);
//        template.convertAndSend(exchange, userBalanceUnblockedRoutingKey, json);
//    }
//
//    @RabbitListener(queues = "${custom.message-broker.listen-queues.process-user-balance-command.name}")
//    @Transactional
//    public void listenProcessUserBalanceCommand(String message) throws UserNotFoundException {
//        log.info("Listening process user balance command: {}", message);
//        ProcessUserBalanceCommand command = mapper.processUserBalanceCommand(message);
//
//        Balance balance = repo.findById(UUID.fromString(command.userId()))
//                .orElseThrow(() -> new UserNotFoundException("User with id %s does not exist.".formatted(command.userId())));
//
//        processBalance(balance, command);
//        balance.setIsBlocked(false);
//        repo.save(balance);
//
//        String json = mapper.userBalanceProcessedEvent(command);
//        template.convertAndSend(exchange, userBalanceProcessedRoutingKey, json);
//    }
//
//    private void processBalance(Balance balance, ProcessUserBalanceCommand command) {
//        switch (command.transactionType()) {
//            case "DEBIT":
//                balance.setBalance(balance.getBalance().subtract(command.amount()));
//                break;
//            case "CREDIT":
//                balance.setBalance(balance.getBalance().add(command.amount()));
//                break;
//        }
//    }
//
//    private void checkIsEnoughCoins(BigDecimal balance, BigDecimal price) throws NotEnoughCoinsOnBalanceException {
//        balance = balance.subtract(price);
//        if (balance.intValueExact() < 0) throw new NotEnoughCoinsOnBalanceException("On the balance, there aren't enough coins to purchase.");
//    }
//}
