package com.example.catalogueservice.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import command.GetGoodsInfoCommand;
import event.GoodsInfoReceiveFailedEvent;
import event.GoodsInfoReceivedEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import com.example.catalogueservice.entity.Product;
import com.example.catalogueservice.mapper.EventMapper;

@Component
@RequiredArgsConstructor
public class DefaultEventMapper implements EventMapper {
    private final ObjectMapper serializer;

    @SneakyThrows
    @Override
    public GetGoodsInfoCommand getGoodsInfoCommand(String message) {
        return serializer.readValue(message, GetGoodsInfoCommand.class);
    }

    @Override
    public GoodsInfoReceivedEvent goodsInfoReceivedEvent(Product product, GetGoodsInfoCommand command) {
        return new GoodsInfoReceivedEvent(
                command.orderId(),
                command.userId(),
                product.getId(),
                product.getPrice()
        );
    }

    @SneakyThrows
    @Override
    public String message(GoodsInfoReceivedEvent event) {
        return serializer.writeValueAsString(event);
    }

    @SneakyThrows
    @Override
    public GoodsInfoReceiveFailedEvent goodsInfoReceiveFailedEvent(GetGoodsInfoCommand command) {
        return new GoodsInfoReceiveFailedEvent(command.orderId());
    }

    @SneakyThrows
    @Override
    public String message(GoodsInfoReceiveFailedEvent event) {
        return serializer.writeValueAsString(event);
    }
}
