//package com.example.catalogueservice.mapper;
//
//import command.GetGoodsInfoCommand;
//import event.GoodsInfoReceiveFailedEvent;
//import event.GoodsInfoReceivedEvent;
//import com.example.catalogueservice.entity.Product;
//
//public interface EventMapper {
//    GetGoodsInfoCommand getGoodsInfoCommand(String message);
//
//    GoodsInfoReceivedEvent goodsInfoReceivedEvent(Product product, GetGoodsInfoCommand command);
//
//    String message(GoodsInfoReceivedEvent event);
//
//    GoodsInfoReceiveFailedEvent goodsInfoReceiveFailedEvent(GetGoodsInfoCommand command);
//
//    String message(GoodsInfoReceiveFailedEvent event);
//}
