package re1kur.balanceservice.configuration;

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
    @Value("${custom.message-broker.exchange}")
    private String exchange;

    @Value("${custom.message-broker.publish-queues.balance-already-blocked-queue.name}")
    private String balanceAlreadyBlockedQueueName;
    @Value("${custom.message-broker.publish-queues.balance-already-blocked-queue.routing-key}")
    private String balanceAlreadyBlockedQueueRoutingKey;

    @Value("${custom.message-broker.publish-queues.user-balance-blocked-queue.name}")
    private String userBalanceBlockedQueueName;
    @Value("${custom.message-broker.publish-queues.user-balance-blocked-queue.routing-key}")
    private String userBalanceBlockedQueueRoutingKey;

    @Value("${custom.message-broker.publish-queues.user-balance-block-failed-queue.name}")
    private String userBalanceBlockFailedQueueName;
    @Value("${custom.message-broker.publish-queues.user-balance-block-failed-queue.routing-key}")
    private String userBalanceBlockFailedQueueRoutingKey;

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Queue userBalanceBlockFailedQueue() {
        return new Queue(userBalanceBlockFailedQueueName);
    }

    @Bean
    public Binding userBalanceBlockFailedBinding() {
        return BindingBuilder
                .bind(userBalanceBlockFailedQueue())
                .to(exchange())
                .with(userBalanceBlockFailedQueueRoutingKey);
    }

    @Bean
    public Queue userBalanceBlockedQueue() {
        return new Queue(userBalanceBlockedQueueName);
    }

    @Bean
    public Binding userBalanceBlockedBinding() {
        return BindingBuilder
                .bind(userBalanceBlockedQueue())
                .to(exchange())
                .with(userBalanceBlockedQueueRoutingKey);
    }

    @Bean
    public Queue balanceAlreadyBlockedQueue() {
        return new Queue(balanceAlreadyBlockedQueueName);
    }

    @Bean
    public Binding balanceAlreadyBlockedBinding() {
        return BindingBuilder
                .bind(balanceAlreadyBlockedQueue())
                .to(exchange())
                .with(balanceAlreadyBlockedQueueRoutingKey);
    }

}
