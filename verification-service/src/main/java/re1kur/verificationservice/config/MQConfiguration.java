package re1kur.verificationservice.config;

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
    @Value("${custom.message-broker.publish-queues.code-generation.name}")
    private String codeGenerationQueue;
    @Value("${custom.message-broker.publish-queues.code-generation.routing-key}")
    private String codeGenerationRoutingKey;
    @Value("${custom.message-broker.publish-queues.verification.name}")
    private String verificationQueue;
    @Value("${custom.message-broker.publish-queues.verification.routing-key}")
    private String verificationRoutingKey;

    @Value("${custom.message-broker.exchange}")
    private String verificationServiceExchange;

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(verificationServiceExchange);
    }

    @Bean
    public Queue codeGenerationQueue() {
        return new Queue(codeGenerationQueue);
    }

    @Bean
    public Queue verificationQueue() {
        return new Queue(verificationQueue);
    }

    @Bean
    public Binding codeGenerationBinding() {
        return BindingBuilder
                .bind(codeGenerationQueue())
                .to(exchange())
                .with(codeGenerationRoutingKey);
    }

    @Bean
    public Binding verificationBinding() {
        return BindingBuilder
                .bind(verificationQueue())
                .to(exchange())
                .with(verificationRoutingKey);
    }
}
