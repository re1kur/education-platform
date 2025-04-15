package re1kur.transactionservice.configuration;

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
    private String exchangeName;

    @Value("${custom.message-broker.publish-queues.transaction-created.name}")
    private String transactionCreatedQueueName;
    @Value("${custom.message-broker.publish-queues.transaction-created.routing-key}")
    private String transactionCreatedQueueRoutingKey;

    @Value("${custom.message-broker.publish-queues.transaction-create-failed.name}")
    private String transactionCreateFailedQueueName;
    @Value("${custom.message-broker.publish-queues.transaction-create-failed.routing-key}")
    private String transactionCreateFailedQueueRoutingKey;

    @Value("${custom.message-broker.publish-queues.transaction-completed.name}")
    private String transactionCompletedQueueName;
    @Value("${custom.message-broker.publish-queues.transaction-completed.routing-key}")
    private String transactionCompletedQueueRoutingKey;

    @Value("${custom.message-broker.publish-queues.transaction-complete-failed.name}")
    private String transactionCompleteFailedQueueName;
    @Value("${custom.message-broker.publish-queues.transaction-complete-failed.routing-key}")
    private String transactionCompleteFailedQueueRoutingKey;

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Queue transactionCompletedQueue() {
        return new Queue(transactionCompletedQueueName);
    }

    @Bean
    public Binding transactionCompletedBinding() {
        return BindingBuilder
                .bind(transactionCompletedQueue())
                .to(exchange())
                .with(transactionCompletedQueueRoutingKey);
    }

    @Bean
    public Queue transactionCompleteFailedQueue() {
        return new Queue(transactionCompleteFailedQueueName);
    }

    @Bean
    public Binding transactionCompleteFailedBinding() {
        return BindingBuilder
                .bind(transactionCompleteFailedQueue())
                .to(exchange())
                .with(transactionCompleteFailedQueueRoutingKey);
    }

    @Bean
    public Queue transactionCreateFailedQueue() {
        return new Queue(transactionCreateFailedQueueName);
    }

    @Bean
    public Binding transactionCreateFailedBinding() {
        return BindingBuilder
                .bind(transactionCreateFailedQueue())
                .to(exchange())
                .with(transactionCreateFailedQueueRoutingKey);
    }

    @Bean
    public Queue transactionCreatedQueue() {
        return new Queue(transactionCreatedQueueName);
    }

    @Bean
    public Binding transactionCreatedBinding() {
        return BindingBuilder
                .bind(transactionCreatedQueue())
                .to(exchange())
                .with(transactionCreatedQueueRoutingKey);
    }

}
