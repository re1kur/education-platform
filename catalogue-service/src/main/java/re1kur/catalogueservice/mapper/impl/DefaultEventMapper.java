package re1kur.catalogueservice.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import command.GetGoodsInfoCommand;
import event.GoodsInfoReceiveFailedEvent;
import event.GoodsInfoReceivedEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import re1kur.catalogueservice.entity.Goods;
import re1kur.catalogueservice.mapper.EventMapper;

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
    public GoodsInfoReceivedEvent goodsInfoReceivedEvent(Goods goods, GetGoodsInfoCommand command) {
        return new GoodsInfoReceivedEvent(
                command.orderId(),
                command.userId(),
                goods.getId(),
                goods.getPrice()
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
