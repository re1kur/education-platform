package re1kur.verificationservice.service;

import exception.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import payload.VerificationPayload;
import re1kur.verificationservice.client.AuthenticationClient;
import re1kur.verificationservice.entity.Code;
import re1kur.verificationservice.generator.CodeGenerator;
import re1kur.verificationservice.mq.publisher.EventPublisher;
import re1kur.verificationservice.repository.CodeRepository;
import re1kur.verificationservice.service.impl.DefaultCodeService;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DefaultCodeServiceTest {
    @InjectMocks
    private DefaultCodeService service;

    @Mock
    private CodeRepository repo;

    @Mock
    private CodeGenerator generator;

    @Mock
    private EventPublisher publisher;

    @Mock
    private AuthenticationClient client;

    @Test
    public void testGenerateCodeWhenUserValid() throws UserNotFoundException, UserAlreadyVerifiedException {
        String email = "email@example.com";
        Code code = Code.builder().email("email@example.com").content("content").build();

        Mockito.when(generator.generate("email@example.com")).thenReturn(code);

        Assertions.assertDoesNotThrow(() -> service.generateCode(email));

        Mockito.verify(client, Mockito.times(1)).checkVerification("email@example.com");
        Mockito.verify(generator, Mockito.times(1)).generate("email@example.com");
        Mockito.verify(repo, Mockito.times(1)).save(Mockito.any(Code.class));
        Mockito.verify(publisher, Mockito.times(1)).publishGenerationCodeEvent(Mockito.any(Code.class));
    }

    @Test
    public void testGenerateCodeWhenUserInvalid() throws UserNotFoundException, UserAlreadyVerifiedException {
        String email = "email@example.com";

        Mockito.doThrow(UserNotFoundException.class).when(client).checkVerification("email@example.com");


        Assertions.assertThrows(UserNotFoundException.class, () -> service.generateCode(email));

        Mockito.verify(client, Mockito.times(1)).checkVerification("email@example.com");
        Mockito.verifyNoInteractions(generator);
    }

    @Test
    public void testGenerateCodeWhenUserAlreadyVerified() throws UserAlreadyVerifiedException, UserNotFoundException {
        String email = "email@example.com";

        Mockito.doThrow(UserAlreadyVerifiedException.class).when(client).checkVerification("email@example.com");

        Assertions.assertThrows(UserAlreadyVerifiedException.class, () -> service.generateCode(email));

        Mockito.verify(client, Mockito.times(1)).checkVerification("email@example.com");
        Mockito.verifyNoInteractions(generator);
    }

    @Test
    public void testVerifyUserValid() {
        VerificationPayload payload = new VerificationPayload("email@example.com", "content");
        Code expectedCode = Code.builder().email("email@example.com").content("content").expiresAt(LocalDateTime.now().plusDays(1)).build();

        Mockito.when(repo.findById("email@example.com")).thenReturn(Optional.of(expectedCode));

        Assertions.assertDoesNotThrow(() -> service.verify(payload));

        Mockito.verify(repo, Mockito.times(1)).findById("email@example.com");
    }

    @Test
    public void testVerifyUserWhenUserAlreadyVerified() throws UserNotFoundException, UserAlreadyVerifiedException {
        VerificationPayload payload = new VerificationPayload("email@example.com", "content");

        Mockito.doThrow(UserAlreadyVerifiedException.class).when(client).checkVerification("email@example.com");

        Assertions.assertThrows(UserAlreadyVerifiedException.class, () -> service.verify(payload));

        Mockito.verifyNoInteractions(repo);
    }

    @Test
    public void testVerifyUserWhenVerificationCodeNotFound() {
        VerificationPayload payload = new VerificationPayload("email@example.com", "content");

        Mockito.when(repo.findById("email@example.com")).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundCodeVerificationException.class, () -> service.verify(payload));

        Mockito.verifyNoInteractions(publisher);
    }

    @Test
    public void testVerifyUserWhenVerificationCodeNotMatches() {
        VerificationPayload payload = new VerificationPayload("email@example.com", "content");
        Code code = Code.builder().email("email@example.com").expiresAt(LocalDateTime.now().plusDays(1)).content("otherContent").build();

        Mockito.when(repo.findById("email@example.com")).thenReturn(Optional.of(code));

        Assertions.assertThrows(IncorrectCodeVerification.class, () -> service.verify(payload));

        Mockito.verifyNoInteractions(publisher);
    }

    @Test
    public void testVerifyUserWhenVerificationCodeExpired() {
        VerificationPayload payload = new VerificationPayload("email@example.com", "content");
        Code code = Code.builder().email("email@example.com").expiresAt(LocalDateTime.now().minusDays(1)).content("content").build();

        Mockito.when(repo.findById("email@example.com")).thenReturn(Optional.of(code));

        Assertions.assertThrows(ExpiredCodeVerificationException.class, () -> service.verify(payload));

        Mockito.verifyNoInteractions(publisher);
    }
}