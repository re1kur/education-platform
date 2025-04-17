package re1kur.verificationservice.configuration;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableDiscoveryClient
@EnableRabbit
public class ApplicationConfiguration {
    @Value("${custom.services.authentication-service.url}")
    private String authenticationServiceUrl;

    @Bean
    @LoadBalanced
    public WebClient.Builder clientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient client(WebClient.Builder clientBuilder) {
        return clientBuilder.baseUrl(authenticationServiceUrl).build();
    }
}
