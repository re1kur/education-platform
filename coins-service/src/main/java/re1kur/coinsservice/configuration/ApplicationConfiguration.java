package re1kur.coinsservice.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDiscoveryClient
public class ApplicationConfiguration {

    @Bean
    public ObjectMapper serializer() {
        return new ObjectMapper();
    }
}
