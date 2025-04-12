package re1kur.authenticationservice.config;

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
    @Value("${custom.rabbitmq.publish-queues.user-registration.name}")
    private String userRegistrationQueue;

    @Value("${custom.rabbitmq.publish-queues.user-registration.routing-key}")
    private String userRegistrationQueueRoutingKey;

    @Value("${custom.rabbitmq.exchange}")
    private String authenticationServiceExchange;

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(authenticationServiceExchange);
    }

    @Bean
    public Queue userRegistrationQueue() {
        return new Queue(userRegistrationQueue);
    }

    @Bean
    public Binding userRegistrationBinding() {
        return BindingBuilder
                .bind(userRegistrationQueue())
                .to(exchange())
                .with(userRegistrationQueueRoutingKey);
    }
}
