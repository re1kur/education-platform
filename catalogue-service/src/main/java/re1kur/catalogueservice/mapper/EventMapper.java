package re1kur.catalogueservice.mapper;

import command.GetGoodsInfoCommand;
import event.GoodsInfoReceiveFailedEvent;
import event.GoodsInfoReceivedEvent;
import re1kur.catalogueservice.entity.Goods;

public interface EventMapper {
    GetGoodsInfoCommand getGoodsInfoCommand(String message);

    GoodsInfoReceivedEvent goodsInfoReceivedEvent(Goods goods, GetGoodsInfoCommand command);

    String message(GoodsInfoReceivedEvent event);

    GoodsInfoReceiveFailedEvent goodsInfoReceiveFailedEvent(GetGoodsInfoCommand command);

    String message(GoodsInfoReceiveFailedEvent event);
}
