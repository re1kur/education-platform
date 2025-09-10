//package com.example.balanceservice.mq.listener;
//
//import exception.OrderNotFoundException;
//import exception.StatusNotFoundException;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import re1kur.orderservice.entity.Order;
//import re1kur.orderservice.entity.Status;
//import re1kur.orderservice.mapper.EventMapper;
//import re1kur.orderservice.repository.OrderRepository;
//import re1kur.orderservice.repository.StatusRepository;
//
//import java.util.UUID;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class DefaultListener {
//    private final EventMapper mapper;
//    private final OrderRepository repo;
//    private final StatusRepository statusRepo;
//
//    @RabbitListener(queues = "${custom.message-broker.listen-queues.reject-priority-command.name}")
//    @Transactional
//    public void listenRejectOrderCommand(String message) throws OrderNotFoundException, StatusNotFoundException {
//        log.info("Listening reject priority by command: {}.", message);
//        String orderId = mapper.rejectOrderCommand(message);
//        Order priority = repo.findById(UUID.fromString(orderId)).orElseThrow(() -> new OrderNotFoundException("Order with id '%s' does not exist.".formatted(orderId)));
//        Status rejectStatus = statusRepo.findByName("REJECTED").orElseThrow(() -> new StatusNotFoundException("Status 'REJECT' does not exists."));
//
//        priority.setStatus(rejectStatus);
//        repo.save(priority);
//        log.info("Order rejected: {}.", priority);
//    }
//
//    @RabbitListener(queues = "${custom.message-broker.listen-queues.approve-priority-command.name}")
//    @Transactional
//    public void listenApproveOrderCommand(String message) throws OrderNotFoundException, StatusNotFoundException {
//        log.info("Listening approve priority by command: {}.", message);
//        String orderId = mapper.approveOrderCommand(message);
//        Order priority = repo.findById(UUID.fromString(orderId)).orElseThrow(() -> new OrderNotFoundException("Order with id '%s' does not exist.".formatted(orderId)));
//        Status approveStatus = statusRepo.findByName("APPROVED").orElseThrow(() -> new StatusNotFoundException("Status 'APPROVED' does not exists."));
//
//        priority.setStatus(approveStatus);
//        repo.save(priority);
//        log.info("Order approved: {}.", priority);
//    }
//
//    @RabbitListener(queues = "${custom.message-broker.listen-queues.create-transaction-command.name}")
//    @Transactional
//    public void listenCreateTransactionCommand(String message) {
//        log.info("Listening create transaction command: {}", message);
//        CreateTransactionCommand command = eventMapper.createTransactionCommand(message);
//        try {
//            Transaction transaction = mapper.create(command);
//            transaction = repo.save(transaction);
//
//            String json = eventMapper.transactionCreatedEvent(command, transaction.getId().toString());
//
//            template.convertAndSend(exchange, transactionCreatedQueueRoutingKey, json);
//            log.info("Created transaction: {}", json);
//        } catch (Exception e) {
//            log.error("Create transaction failed: {}", e.getMessage());
//            String json = eventMapper.transactionCreateFailedEvent(command);
//
//            template.convertAndSend(exchange, transactionCreateFailedQueueRoutingKey, json);
//        }
//    }
//
//    @RabbitListener(queues = "${custom.message-broker.listen-queues.complete-transaction-command.name}")
//    @Transactional
//    public void listenCompleteTransactionCommand(String message) throws StatusNotFoundException, TransactionTypeNotFoundException, TransactionNotFoundException {
//        log.info("Listening complete transaction command: {}", message);
//        CompleteTransactionCommand command = eventMapper.completeTransactionCommand(message);
//        Transaction transaction = repo.findById(UUID.fromString(command.transactionId()))
//                .orElseThrow(() -> new TransactionNotFoundException(
//                        "Transaction with id '%s' does not exist.".formatted(command.transactionId())));
//
//        transaction = mapper.complete(transaction);
//
//        repo.save(transaction);
//
//        String json = eventMapper.transactionCompletedEvent(command);
//
//        template.convertAndSend(exchange, transactionCompletedQueueRoutingKey, json);
//        log.info("Completed transaction: {}", transaction);
//
//    }
//
//
//    @RabbitListener(queues = "${custom.message-broker.listen-queues.reject-priority-command.name}")
//    @Transactional
//    public void listenRejectOrderCommand(String message) throws OrderNotFoundException, StatusNotFoundException {
//        log.info("Listening reject priority by command: {}.", message);
//        String orderId = mapper.rejectOrderCommand(message);
//        Order priority = repo.findById(UUID.fromString(orderId)).orElseThrow(() -> new OrderNotFoundException("Order with id '%s' does not exist.".formatted(orderId)));
//        Status rejectStatus = statusRepo.findByName("REJECTED").orElseThrow(() -> new StatusNotFoundException("Status 'REJECT' does not exists."));
//
//        priority.setStatus(rejectStatus);
//        repo.save(priority);
//        log.info("Order rejected: {}.", priority);
//    }
//
//    @RabbitListener(queues = "${custom.message-broker.listen-queues.approve-priority-command.name}")
//    @Transactional
//    public void listenApproveOrderCommand(String message) throws OrderNotFoundException, StatusNotFoundException {
//        log.info("Listening approve priority by command: {}.", message);
//        String orderId = mapper.approveOrderCommand(message);
//        Order priority = repo.findById(UUID.fromString(orderId)).orElseThrow(() -> new OrderNotFoundException("Order with id '%s' does not exist.".formatted(orderId)));
//        Status approveStatus = statusRepo.findByName("APPROVED").orElseThrow(() -> new StatusNotFoundException("Status 'APPROVED' does not exists."));
//
//        priority.setStatus(approveStatus);
//        repo.save(priority);
//        log.info("Order approved: {}.", priority);
//    }
//}
