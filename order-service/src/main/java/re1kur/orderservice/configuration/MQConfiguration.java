package re1kur.orderservice.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class MQConfiguration {
    @Value("${custom.message-broker.publish-queues.order-created.name}")
    private String orderCreatedQueueName;
    @Value("${custom.message-broker.publish-queues.order-created.routing-key}")
    private String orderCreatedQueueRoutingKey;
    @Value("${custom.message-broker.exchange}")
    private String orderExchange;


    @Value("${custom.saga.exchange}")
    private String sagaExchange;
    @Value("${custom.saga.publish-queues.get-goods-info-command.name}")
    private String getGoodsInfoCommandQueueName;
    @Value("${custom.saga.publish-queues.get-goods-info-command.routing-key}")
    private String getGoodsInfoCommandQueueRoutingKey;

    @Value("${custom.saga.publish-queues.block-user-balance-command.name}")
    private String blockUserBalanceCommandQueueName;
    @Value("${custom.saga.publish-queues.block-user-balance-command.routing-key}")
    private String blockUserBalanceCommandQueueRoutingKey;

    @Value("${custom.saga.publish-queues.reject-order-command.name}")
    private String orderRejectCommandQueueName;
    @Value("${custom.saga.publish-queues.reject-order-command.routing-key}")
    private String orderRejectCommandQueueRoutingKey;

    @Value("${custom.saga.publish-queues.create-transaction-command.name}")
    private String createTransactionCommandQueueName;
    @Value("${custom.saga.publish-queues.create-transaction-command.routing-key}")
    private String createTransactionCommandQueueRoutingKey;


    @Bean
    public TopicExchange sagaExchange() {
        return new TopicExchange(sagaExchange);
    }

    @Bean
    public Queue createTransactionCommandQueue() {
        return new Queue(createTransactionCommandQueueName);
    }

    @Bean
    public Binding createTransactionCommandBinding() {
        return BindingBuilder
                .bind(createTransactionCommandQueue())
                .to(sagaExchange())
                .with(createTransactionCommandQueueRoutingKey);
    }

    @Bean
    public Queue orderRejectCommandQueue() {
        return new Queue(orderRejectCommandQueueName);
    }

    @Bean
    public Binding orderRejectCommandBinding() {
        return BindingBuilder
                .bind(orderRejectCommandQueue())
                .to(sagaExchange())
                .with(orderRejectCommandQueueRoutingKey);
    }

    @Bean
    public Queue blockUserBalanceCommandQueue() {
        return new Queue(blockUserBalanceCommandQueueName);
    }

    @Bean
    public Binding blockUserBalanceCommandBinding() {
        return BindingBuilder
                .bind(blockUserBalanceCommandQueue())
                .to(sagaExchange())
                .with(blockUserBalanceCommandQueueRoutingKey);
    }

    @Bean
    public Queue getGoodsInfoCommandQueue() {
        return new Queue(getGoodsInfoCommandQueueName);
    }

    @Bean
    public Binding getGoodsInfoCommandBinding() {
        return BindingBuilder
                .bind(getGoodsInfoCommandQueue())
                .to(sagaExchange())
                .with(getGoodsInfoCommandQueueRoutingKey);
    }

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(orderExchange);
    }

    @Bean
    public Queue orderCreatedQueue() {
        return new Queue(orderCreatedQueueName);
    }

    @Bean
    public Binding orderCreatedBinding() {
        return BindingBuilder
                .bind(orderCreatedQueue())
                .to(orderExchange())
                .with(orderCreatedQueueRoutingKey);
    }
}
