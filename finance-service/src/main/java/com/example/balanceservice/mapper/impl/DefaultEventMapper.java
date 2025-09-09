//package com.example.balanceservice.mapper.impl;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import command.ApproveOrderCommand;
//import command.RejectOrderCommand;
//import event.OrderCreatedEvent;
//import lombok.RequiredArgsConstructor;
//import lombok.SneakyThrows;
//import org.springframework.stereotype.Component;
//import re1kur.orderservice.mapper.EventMapper;
//
//@Component
//@RequiredArgsConstructor
//public class DefaultEventMapper implements EventMapper {
//    private final ObjectMapper serializer;
//
//    @SneakyThrows
//    @Override
//    public String message(OrderCreatedEvent event) {
//        return serializer.writeValueAsString(event);
//    }
//
//    @SneakyThrows
//    @Override
//    public String rejectOrderCommand(String message) {
//        RejectOrderCommand command = serializer.readValue(message, RejectOrderCommand.class);
//        return command.orderId();
//    }
//
//    @SneakyThrows
//    @Override
//    public String approveOrderCommand(String message) {
//        ApproveOrderCommand command = serializer.readValue(message, ApproveOrderCommand.class);
//        return command.orderId();
//    }
//
//    @SneakyThrows
//    @Override
//    public CreateTransactionCommand createTransactionCommand(String message) {
//        return serializer.readValue(message, CreateTransactionCommand.class);
//    }
//
//    @SneakyThrows
//    @Override
//    public String transactionCreatedEvent(CreateTransactionCommand command, String transactionId) {
//        TransactionCreatedEvent event = new TransactionCreatedEvent(
//                command.orderId(),
//                command.userId(),
//                transactionId,
//                command.transactionType(),
//                command.amount());
//        return serializer.writeValueAsString(event);
//    }
//
//    @SneakyThrows
//    @Override
//    public String transactionCreateFailedEvent(CreateTransactionCommand command) {
//        TransactionCreateFailedEvent event = new TransactionCreateFailedEvent(
//                command.orderId(),
//                command.userId()
//        );
//
//        return serializer.writeValueAsString(event);
//    }
//
//    @SneakyThrows
//    @Override
//    public CompleteTransactionCommand completeTransactionCommand(String message) {
//        return serializer.readValue(message, CompleteTransactionCommand.class);
//    }
//
//    @SneakyThrows
//    @Override
//    public String transactionCompletedEvent(CompleteTransactionCommand command) {
//        TransactionCompletedEvent event = new TransactionCompletedEvent(
//                command.orderId()
//        );
//        return serializer.writeValueAsString(event);
//    }
//}
