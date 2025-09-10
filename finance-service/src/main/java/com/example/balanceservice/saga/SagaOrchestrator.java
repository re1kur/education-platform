//package com.example.balanceservice.saga;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import re1kur.orderservice.mapper.SagaMapper;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class SagaOrchestrator {
//    private final SagaMapper mapper;
//    private final RabbitTemplate template;
//
//    @Value("${custom.saga.exchange}")
//    private String exchange;
//
//    @Value("${custom.saga.publish-queues.get-goods-info-command.routing-key}")
//    private String getGoodsInfoCommandRoutingKey;
//
//    @Value("${custom.saga.publish-queues.reject-priority-command.routing-key}")
//    private String rejectOrderCommandRoutingKey;
//
//    @Value("${custom.saga.publish-queues.block-user-balance-command.routing-key}")
//    private String blockUserBalanceCommandRoutingKey;
//
//    @Value("${custom.saga.publish-queues.create-transaction-command.routing-key}")
//    private String createTransactionCommandRoutingKey;
//
//    @Value("${custom.saga.publish-queues.process-user-balance-command.routing-key}")
//    private String processBalanceCommandRoutingKey;
//
//    @Value("${custom.saga.publish-queues.unblock-user-balance-command.routing-key}")
//    private String unblockUserBalanceCommandRoutingKey;
//
//    @Value("${custom.saga.publish-queues.complete-transaction-command.routing-key}")
//    private String completeTransactionCommandRoutingKey;
//
//    @Value("${custom.saga.publish-queues.approve-priority-command.routing-key}")
//    private String approveOrderCommandRoutingKey;
//
//
//    @RabbitListener(queues = "${custom.saga.handle-queues.priority-created.name}")
//    public void handleOrderCreatedEvent(String message) {
//        log.info("Handling priority created event: {}", message);
//        String jsonCommand = mapper.getGoodsInfoCommand(message);
//
//        template.convertAndSend(exchange, getGoodsInfoCommandRoutingKey, jsonCommand);
//    }
//
//    @RabbitListener(queues = "${custom.saga.handle-queues.goods-info-receive-failed.name}")
//    public void handleGoodsInfoReceiveFailedEvent(String message) {
//        log.info("Handling goods info receive failed: {}", message);
//        String orderId = mapper.goodsInfoReceiveFailedEvent(message);
//        String jsonCommand = mapper.rejectOrderCommand(orderId);
//
//        template.convertAndSend(exchange, rejectOrderCommandRoutingKey, jsonCommand);
//    }
//
//    @RabbitListener(queues = "${custom.saga.handle-queues.goods-info-received.name}")
//    public void handleGoodsInfoReceivedEvent(String message) {
//        log.info("Handling goods info received: {}", message);
//        String jsonCommand = mapper.blockUserBalanceCommand(message, "DEBIT");
//
//        template.convertAndSend(exchange, blockUserBalanceCommandRoutingKey, jsonCommand);
//    }
//
//    @RabbitListener(queues = "${custom.saga.handle-queues.user-balance-block-failed-queue.name}")
//    public void handleUserBalanceBlockFailedEvent(String message) {
//        log.info("Handling user balance block failed: {}", message);
//        String orderId = mapper.userBalanceBlockFailedEvent(message);
//        String jsonCommand = mapper.rejectOrderCommand(orderId);
//
//        template.convertAndSend(exchange, rejectOrderCommandRoutingKey, jsonCommand);
//    }
//
//    @RabbitListener(queues = "${custom.saga.handle-queues.user-balance-blocked-queue.name}")
//    public void handleUserBalanceBlockedEvent(String message) {
//        log.info("Handling user balance blocked event: {}", message);
//        String jsonCommand = mapper.userBalanceBlockedEvent(message);
//
//        template.convertAndSend(exchange, createTransactionCommandRoutingKey, jsonCommand);
//    }
//
//    @RabbitListener(queues = "${custom.saga.handle-queues.transaction-created-queue.name}")
//    public void handleTransactionCreatedEvent(String message) {
//        log.info("Handling transaction created event: {}", message);
//        String jsonCommand = mapper.processBalance(message);
//
//        template.convertAndSend(exchange, processBalanceCommandRoutingKey, jsonCommand);
//    }
//
//    @RabbitListener(queues = "${custom.saga.handle-queues.transaction-create-failed-queue.name}")
//    public void handleTransactionCreateFailedEvent(String message) {
//        log.info("Handling transaction create failed event: {}", message);
//        String jsonCommand = mapper.unblockUserBalanceCommand(message);
//
//        template.convertAndSend(exchange, unblockUserBalanceCommandRoutingKey, jsonCommand);
//    }
//
//    @RabbitListener(queues = "${custom.saga.handle-queues.user-balance-unblocked-queue.name}")
//    public void handleUserBalanceUnblockedEvent(String message) {
//        log.info("Handling user balance unblocked event: {}", message);
//        String orderId = mapper.userBalanceUnblockedEvent(message);
//        String jsonCommand = mapper.rejectOrderCommand(orderId);
//
//        template.convertAndSend(exchange, rejectOrderCommandRoutingKey, jsonCommand);
//    }
//
//    @RabbitListener(queues = "${custom.saga.handle-queues.user-balance-processed-queue.name}")
//    public void handleUserBalanceProcessedEvent(String message) {
//        log.info("Handling user balance processed event: {}", message);
//        String jsonCommand = mapper.completeTransactionCommand(message);
//
//        template.convertAndSend(exchange, completeTransactionCommandRoutingKey, jsonCommand);
//    }
//
//    @RabbitListener(queues = "${custom.saga.handle-queues.transaction-completed-queue.name}")
//    public void handleTransactionCompletedEvent(String message) {
//        log.info("Handling transaction completed event: {}", message);
//        String jsonCommand = mapper.approveOrderCommand(message);
//
//        template.convertAndSend(exchange, approveOrderCommandRoutingKey, jsonCommand);
//    }
//}
