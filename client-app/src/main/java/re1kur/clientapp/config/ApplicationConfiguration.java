package re1kur.clientapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApplicationConfiguration {
    @Value("${backend.baseUrl}")
    private String baseUrl;

    @Bean
    public WebClient client(WebClient.Builder builder) {
        return builder
                .baseUrl(baseUrl)
                .build();
    }
}
