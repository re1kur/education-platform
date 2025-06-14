package re1kur.verificationservice.client;

import dto.CheckVerificationResult;
import exception.UserAlreadyVerifiedException;
import exception.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import re1kur.verificationservice.client.impl.DefaultAuthenticationClient;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith({MockitoExtension.class})
class DefaultAuthenticationClientTest {
    @InjectMocks
    private DefaultAuthenticationClient client;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private WebClient webClient;

    @Test
    void testCheckVerificationWhenUserExistsAndNotVerified() {
        String email = "email@example.com";
        CheckVerificationResult result = new CheckVerificationResult(true, false);

        Mockito.when(webClient.get().uri(any(Function.class)).retrieve().bodyToMono(CheckVerificationResult.class))
                .thenReturn(Mono.just(result));

        Assertions.assertDoesNotThrow(() -> client.checkVerification(email));
    }

    @Test
    void testCheckVerificationWhenUserExistsButVerified() {
        String email = "email@example.com";
        CheckVerificationResult result = new CheckVerificationResult(true, true);

        Mockito.when(webClient.get().uri(any(Function.class)).retrieve().bodyToMono(CheckVerificationResult.class))
                .thenReturn(Mono.just(result));

        Assertions.assertThrows(UserAlreadyVerifiedException.class, () -> client.checkVerification(email));
    }

    @Test
    void testCheckVerificationWhenUserNotFound() {
        String email = "email@example.com";
        CheckVerificationResult result = new CheckVerificationResult(false, false);

        Mockito.when(webClient.get().uri(any(Function.class)).retrieve().bodyToMono(CheckVerificationResult.class))
                .thenReturn(Mono.just(result));

        Assertions.assertThrows(UserNotFoundException.class, () -> client.checkVerification(email));
    }
}