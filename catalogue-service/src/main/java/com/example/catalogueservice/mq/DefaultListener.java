//package com.example.catalogueservice.mq;
//
//import command.GetGoodsInfoCommand;
//import event.GoodsInfoReceiveFailedEvent;
//import event.GoodsInfoReceivedEvent;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import com.example.catalogueservice.entity.Goods;
//import com.example.catalogueservice.exception.GoodsNotFoundException;
//import com.example.catalogueservice.mapper.EventMapper;
//import com.example.catalogueservice.repository.GoodsRepository;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class DefaultListener {
//    private final EventMapper mapper;
//    private final GoodsRepository repo;
//    private final RabbitTemplate template;
//
//    @Value("${custom.message-broker.exchange}")
//    private String exchange;
//    @Value("${custom.message-broker.publish-queues.goods-info-received.routing-key}")
//    private String goodsInfoReceivedQueueRoutingKey;
//    @Value("${custom.message-broker.publish-queues.goods-info-receive-failed.routing-key}")
//    private String goodsInfoReceiveFailedQueueRoutingKey;
//
//    @RabbitListener(queues = "${custom.message-broker.listen-queues.get-goods-info-command.name}")
//    public void listenGetGoodsInfoCommand(String message) {
//        log.info("Listening GetGoodsInfoCommand: {}", message);
//        GetGoodsInfoCommand command = mapper.getGoodsInfoCommand(message);
//        try {
//            Goods goods = repo.findById(command.goodsId())
//                    .orElseThrow(() -> new GoodsNotFoundException("Goods with id %d does not exist.".formatted(command.goodsId())));
//
//            GoodsInfoReceivedEvent event = mapper.goodsInfoReceivedEvent(goods, command);
//            String json = mapper.message(event);
//
//            template.convertAndSend(exchange, goodsInfoReceivedQueueRoutingKey, json);
//        } catch (Exception e) {
//            handleExceptionWhileListening(e, command);
//        }
//    }
//
//    private void handleExceptionWhileListening(Exception e, GetGoodsInfoCommand command) {
//        log.error("Error while listening GetGoodsInfoCommand by saga : \n{}", e.getMessage());
//        GoodsInfoReceiveFailedEvent event = mapper.goodsInfoReceiveFailedEvent(command);
//
//        String json = mapper.message(event);
//        template.convertAndSend(exchange, goodsInfoReceiveFailedQueueRoutingKey, json);
//    }
//}
