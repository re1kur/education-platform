//package com.example.balanceservice.mapper.impl;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import command.*;
//import event.*;
//import lombok.RequiredArgsConstructor;
//import lombok.SneakyThrows;
//import org.springframework.stereotype.Component;
//import re1kur.orderservice.mapper.SagaMapper;
//
//@Component
//@RequiredArgsConstructor
//public class DefaultSagaMapper implements SagaMapper {
//    private final ObjectMapper serializer;
//
//    @SneakyThrows
//    @Override
//    public String getGoodsInfoCommand(String message) {
//        OrderCreatedEvent event = serializer.readValue(message, OrderCreatedEvent.class);
//        GetGoodsInfoCommand command = new GetGoodsInfoCommand(
//                event.orderId(),
//                event.userId(),
//                event.goodsId());
//        return serializer.writeValueAsString(command);
//    }
//
//    @SneakyThrows
//    @Override
//    public String blockUserBalanceCommand(String message, String transactionType) {
//        GoodsInfoReceivedEvent event = serializer.readValue(message, GoodsInfoReceivedEvent.class);
//        BlockUserBalanceCommand command = new BlockUserBalanceCommand(
//                event.orderId(),
//                event.userId(),
//                event.price(),
//                transactionType
//        );
//        return serializer.writeValueAsString(command);
//    }
//
//    @SneakyThrows
//    @Override
//    public String rejectOrderCommand(String orderId) {
//        RejectOrderCommand command = new RejectOrderCommand(orderId);
//        return serializer.writeValueAsString(command);
//    }
//
//    @SneakyThrows
//    @Override
//    public String goodsInfoReceiveFailedEvent(String message) {
//        GoodsInfoReceiveFailedEvent event = serializer.readValue(message, GoodsInfoReceiveFailedEvent.class);
//        return event.orderId();
//    }
//
//    @SneakyThrows
//    @Override
//    public String userBalanceBlockFailedEvent(String message) {
//        UserBalanceBlockFailedEvent event = serializer.readValue(message, UserBalanceBlockFailedEvent.class);
//        return event.orderId();
//    }
//
//    @SneakyThrows
//    public String userBalanceBlockedEvent(String message) {
//        UserBalanceBlockedEvent event = serializer.readValue(message, UserBalanceBlockedEvent.class);
//        CreateTransactionCommand command = new CreateTransactionCommand(
//                event.orderId(),
//                event.userId(),
//                event.amount(),
//                event.transactionType());
//        return serializer.writeValueAsString(command);
//    }
//
//    @SneakyThrows
//    @Override
//    public String processBalance(String message) {
//        TransactionCreatedEvent event = serializer.readValue(message, TransactionCreatedEvent.class);
//        ProcessUserBalanceCommand command = new ProcessUserBalanceCommand(
//                event.orderId(),
//                event.userId(),
//                event.transactionId(),
//                event.transactionType(),
//                event.amount()
//        );
//        return serializer.writeValueAsString(command);
//    }
//
//    @SneakyThrows
//    @Override
//    public String unblockUserBalanceCommand(String message) {
//        TransactionCreateFailedEvent event = serializer.readValue(message, TransactionCreateFailedEvent.class);
//        UnblockUserBalanceCommand command = new UnblockUserBalanceCommand(
//                event.orderId(),
//                event.userId()
//        );
//        return serializer.writeValueAsString(command);
//    }
//
//    @SneakyThrows
//    @Override
//    public String userBalanceUnblockedEvent(String message) {
//        UserBalanceUnblockedEvent event = serializer.readValue(message, UserBalanceUnblockedEvent.class);
//        return event.orderId();
//    }
//
//    @SneakyThrows
//    @Override
//    public String completeTransactionCommand(String message) {
//        UserBalanceProcessedEvent event = serializer.readValue(message, UserBalanceProcessedEvent.class);
//        CompleteTransactionCommand command = new CompleteTransactionCommand(
//                event.orderId(),
//                event.transactionId()
//        );
//        return serializer.writeValueAsString(command);
//    }
//
//    @SneakyThrows
//    @Override
//    public String approveOrderCommand(String message) {
//        TransactionCompletedEvent event = serializer.readValue(message, TransactionCompletedEvent.class);
//        ApproveOrderCommand command = new ApproveOrderCommand(
//            event.orderId()
//        );
//        return serializer.writeValueAsString(command);
//    }
//
//}