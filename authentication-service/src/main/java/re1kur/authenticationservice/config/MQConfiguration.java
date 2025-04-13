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
    @Value("${custom.rabbitmq.publish-queues.user-registration.balance}")
    private String userRegistrationBalanceQueue;

    @Value("${custom.rabbitmq.publish-queues.user-registration.welcome}")
    private String userWelcomeQueue;

    @Value("${custom.rabbitmq.publish-queues.user-registration.routing-key}")
    private String userRegistrationRoutingKey;

    @Value("${custom.rabbitmq.exchange}")
    private String authenticationServiceExchange;

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(authenticationServiceExchange);
    }

    @Bean
    public Queue userRegistrationBalanceQueue() {
        return new Queue(userRegistrationBalanceQueue);
    }

    @Bean
    public Queue userWelcomeQueue() {
        return new Queue(userWelcomeQueue);
    }

    @Bean
    public Binding userRegistrationBalanceBinding() {
        return BindingBuilder
                .bind(userRegistrationBalanceQueue())
                .to(exchange())
                .with(userRegistrationRoutingKey);
    }

    @Bean
    public Binding userWelcomeBinding() {
        return BindingBuilder
                .bind(userWelcomeQueue())
                .to(exchange())
                .with(userRegistrationRoutingKey);
    }
}
