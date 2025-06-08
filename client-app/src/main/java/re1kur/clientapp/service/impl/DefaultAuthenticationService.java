package re1kur.clientapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import re1kur.clientapp.service.AuthenticationService;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class DefaultAuthenticationService implements AuthenticationService {
    private final WebClient client;

    @Value("${backend.authenticationServiceUrl}")
    private String service;

    @Value("${backend.authentication.loginEndpoint}")
    private String endpoint;

    @Override
    public String authenticate(String email, String password) {
        return client.post()
                .uri(service + endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(Map.of("email", email, "password", password)))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
