package re1kur.verificationservice.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import re1kur.verificationservice.client.AuthenticationClient;
import re1kur.verificationservice.dto.ResultCheckVerification;
import re1kur.verificationservice.exception.UserAlreadyVerifiedException;
import re1kur.verificationservice.exception.UserNotFoundException;

@Component
@RequiredArgsConstructor
public class DefaultAuthenticationClient implements AuthenticationClient {
    private final WebClient client;

    @Override
    public void checkVerification(String email) throws UserNotFoundException, UserAlreadyVerifiedException {
        ResultCheckVerification result = client
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/verification")
                        .queryParam("email", email)
                        .build())
                .retrieve()
                .bodyToMono(ResultCheckVerification.class)
                .block();
        assert result != null;
        if (!result.isExists()) throw new UserNotFoundException("User does not exist.");
        if (result.isVerified()) throw new UserAlreadyVerifiedException("User already verified.");
    }
}
