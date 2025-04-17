package re1kur.mailservice.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDiscoveryClient
@EnableRabbit
public class ApplicationConfiguration {
    @Bean
    public ObjectMapper serializer() {
        return new ObjectMapper();
    }

}
