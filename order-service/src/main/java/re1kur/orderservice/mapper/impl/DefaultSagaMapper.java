package re1kur.orderservice.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import command.BlockUserBalanceCommand;
import command.CreateTransactionCommand;
import command.GetGoodsInfoCommand;
import command.RejectOrderCommand;
import event.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import re1kur.orderservice.mapper.SagaMapper;

@Component
@RequiredArgsConstructor
public class DefaultSagaMapper implements SagaMapper {
    private final ObjectMapper serializer;

    @SneakyThrows
    @Override
    public String getGoodsInfoCommand(String message) {
        OrderCreatedEvent event = serializer.readValue(message, OrderCreatedEvent.class);
        GetGoodsInfoCommand command = new GetGoodsInfoCommand(
                event.orderId(),
                event.userId(),
                event.goodsId());
        return serializer.writeValueAsString(command);
    }

    @SneakyThrows
    @Override
    public String blockUserBalanceCommand(String message, String transactionType) {
        GoodsInfoReceivedEvent event = serializer.readValue(message, GoodsInfoReceivedEvent.class);
        BlockUserBalanceCommand command = new BlockUserBalanceCommand(
                event.orderId(),
                event.userId(),
                event.price(),
                transactionType
        );
        return serializer.writeValueAsString(command);
    }

    @SneakyThrows
    @Override
    public String rejectOrderCommand(String orderId) {
        RejectOrderCommand command = new RejectOrderCommand(orderId);
        return serializer.writeValueAsString(command);
    }

    @SneakyThrows
    @Override
    public String goodsInfoReceiveFailedEvent(String message) {
        GoodsInfoReceiveFailedEvent event = serializer.readValue(message, GoodsInfoReceiveFailedEvent.class);
        return event.orderId();
    }

    @SneakyThrows
    @Override
    public String userBalanceBlockFailedEvent(String message) {
        UserBalanceBlockFailedEvent event = serializer.readValue(message, UserBalanceBlockFailedEvent.class);
        return event.orderId();
    }

    @SneakyThrows
    public String userBalanceBlockedEvent(String message) {
        UserBalanceBlockedEvent event = serializer.readValue(message, UserBalanceBlockedEvent.class);
        CreateTransactionCommand command = new CreateTransactionCommand(
                event.orderId(),
                event.userId(),
                event.amount(),
                event.transactionType());
        return serializer.writeValueAsString(command);
    }

}