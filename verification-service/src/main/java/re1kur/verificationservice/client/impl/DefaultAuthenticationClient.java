package re1kur.verificationservice.client.impl;

import dto.CheckVerificationResult;
import exception.UserAlreadyVerifiedException;
import exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import re1kur.verificationservice.client.AuthenticationClient;

@Component
@RequiredArgsConstructor
public class DefaultAuthenticationClient implements AuthenticationClient {
    private final WebClient client;

    @Override
    public void checkVerification(String email) throws UserNotFoundException, UserAlreadyVerifiedException {
        CheckVerificationResult result = client
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/verification")
                        .queryParam("email", email)
                        .build())
                .retrieve()
                .bodyToMono(CheckVerificationResult.class)
                .block();
        assert result != null;
        if (!result.isExists()) throw new UserNotFoundException("User does not exist.");
        if (result.isVerified()) throw new UserAlreadyVerifiedException("User already verified.");
    }
}
