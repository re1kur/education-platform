package re1kur.catalogueservice.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class MQConfiguration {
    @Value("${custom.message-broker.exchange}")
    private String exchange;
    @Value("${custom.message-broker.publish-queues.goods-info-received.name}")
    private String goodsInfoReceivedQueueName;
    @Value("${custom.message-broker.publish-queues.goods-info-received.routing-key}")
    private String goodsInfoReceivedQueueRoutingKey;

    @Value("${custom.message-broker.publish-queues.goods-info-receive-failed.name}")
    private String goodsInfoReceiveFailedQueueName;
    @Value("${custom.message-broker.publish-queues.goods-info-receive-failed.routing-key}")
    private String goodsInfoReceiveFailedQueueRoutingKey;

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Queue goodsInfoReceivedQueue() {
        return new Queue(goodsInfoReceivedQueueName);
    }

    @Bean
    public Queue goodsInfoReceiveFailedQueue() {
        return new Queue(goodsInfoReceiveFailedQueueName);
    }

    @Bean
    public Binding goodsInfoReceivedBinding() {
        return BindingBuilder
                .bind(goodsInfoReceivedQueue())
                .to(exchange())
                .with(goodsInfoReceivedQueueRoutingKey);
    }

    @Bean
    public Binding goodsInfoReceiveFailedBinding() {
        return BindingBuilder
                .bind(goodsInfoReceiveFailedQueue())
                .to(exchange())
                .with(goodsInfoReceiveFailedQueueRoutingKey);
    }
}
