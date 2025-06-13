package re1kur.authenticationservice.service;

import exception.UserAuthenticationException;
import exception.UserRegistrationException;
import exception.UserVerificationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import payload.UserPayload;
import re1kur.authenticationservice.entity.User;
import re1kur.authenticationservice.jwt.entity.Token;
import re1kur.authenticationservice.jwt.impl.DefaultJwtProvider;
import re1kur.authenticationservice.mapper.impl.DefaultUserMapper;
import re1kur.authenticationservice.mq.publisher.impl.DefaultEventPublisher;
import re1kur.authenticationservice.repository.UserRepository;
import re1kur.authenticationservice.service.impl.DefaultAuthenticationService;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @Mock
    private UserRepository repo;

    @Mock
    private DefaultUserMapper mapper;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Mock
    private DefaultJwtProvider jwtProvider;

    @Mock
    private DefaultEventPublisher publisher;

    @InjectMocks
    private DefaultAuthenticationService service;


    // TEST REGISTER METHOD

    @Test
    public void testRegisterWhenPayloadValid() throws UserRegistrationException {
        UserPayload payload = new UserPayload("email@example.com", "password");
        User expectedUser = User.builder()
                .email("email@example.com")
                .password("hashedPassword")
                .build();

        Mockito.when(mapper.write(payload)).thenReturn(expectedUser);
        Mockito.when(repo.save(Mockito.any(User.class))).thenReturn(expectedUser);

        service.register(payload);

        Mockito.verify(repo, Mockito.times(1)).save(Mockito.any(User.class));
    }

    @Test
    public void testRegisterWhenUserWithEmailAlreadyExists() {
        UserPayload payload = new UserPayload("exists@email.com", "password");

        Mockito.when(repo.existsByEmail("exists@email.com")).thenReturn(true);

        Assertions.assertThrows(UserRegistrationException.class, () -> service.register(payload));
    }

    // TEST AUTHENTICATE METHOD

    @Test
    public void testAuthenticateWhenUserValid() throws UserAuthenticationException {
        UserPayload payload = new UserPayload("email@example.com", "password");
        User validUser = User.builder()
                .email("email@example.com")
                .password("hashedPassword")
                .isEmailVerified(true)
                .build();
        Token expectedToken = new Token("dummy");

        Mockito.when(repo.findByEmail("email@example.com")).thenReturn(Optional.of(validUser));
        Mockito.when(encoder.matches("password", "hashedPassword")).thenReturn(true);
        Mockito.when(jwtProvider.provide(validUser)).thenReturn(expectedToken);

        Assertions.assertEquals(expectedToken, service.authenticate(payload));

        Mockito.verify(repo).findByEmail("email@example.com");
        Mockito.verify(encoder).matches("password", "hashedPassword");
        Mockito.verify(jwtProvider).provide(validUser);
    }

    @Test
    public void testAuthenticateWhenUserNotVerified() {
        UserPayload payload = new UserPayload("email@example.com", "password");
        User notVerifiedUser = User.builder()
                .email("email@example.com")
                .password("hashedPassword")
                .isEmailVerified(false)
                .build();

        Mockito.when(repo.findByEmail("email@example.com")).thenReturn(Optional.of(notVerifiedUser));

        Assertions.assertThrows(UserAuthenticationException.class, () -> service.authenticate(payload));

        Mockito.verify(repo).findByEmail("email@example.com");
    }

    @Test
    public void testAuthenticateWhenUserNotFound() {
        UserPayload payload = new UserPayload("email@example.com", "password");

        Mockito.when(repo.findByEmail("email@example.com")).thenReturn(Optional.empty());

        Assertions.assertThrows(UserAuthenticationException.class, () -> service.authenticate(payload));

        Mockito.verify(repo).findByEmail("email@example.com");
    }

    @Test
    public void testAuthenticateWhenPasswordNotMatch() {
        UserPayload payload = new UserPayload("email@example.com", "password");
        User foundUser = User.builder()
                .email("email@example.com")
                .password("hashedPassword")
                .isEmailVerified(true)
                .build();

        Mockito.when(repo.findByEmail("email@example.com")).thenReturn(Optional.of(foundUser));
        Mockito.when(encoder.matches("password", "hashedPassword")).thenReturn(false);

        Assertions.assertThrows(UserAuthenticationException.class, () -> service.authenticate(payload));

        Mockito.verify(repo).findByEmail("email@example.com");
        Mockito.verify(encoder).matches("password", "hashedPassword");
    }

    @Test
    public void testVerifyWhenUserExists() {
        String email = "email@example.com";
        User expectedUser = User.builder()
                .email("email@example.com")
                .password("hashedPassword")
                .isEmailVerified(false)
                .build();

        Mockito.when(repo.findByEmail("email@example.com")).thenReturn(Optional.of(expectedUser));

        Assertions.assertDoesNotThrow(() -> service.verify(email));

        Mockito.verify(repo).findByEmail("email@example.com");
        Mockito.verify(repo).save(Mockito.any(User.class));
    }

    @Test
    public void testVerifyWhenUserNotFound() {
        String email = "email@example.com";

        Mockito.when(repo.findByEmail("email@example.com")).thenReturn(Optional.empty());

        Assertions.assertThrows(UserVerificationException.class, () -> service.verify(email));

        Mockito.verify(repo).findByEmail("email@example.com");
    }

}