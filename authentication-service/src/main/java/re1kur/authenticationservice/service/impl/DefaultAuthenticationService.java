package re1kur.authenticationservice.service.impl;

import exception.UserVerificationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dto.CheckVerificationResult;
import payload.UserPayload;
import re1kur.authenticationservice.entity.User;
import exception.UserAuthenticationException;
import exception.UserRegistrationException;
import re1kur.authenticationservice.jwt.JwtProvider;
import re1kur.authenticationservice.jwt.entity.Token;
import re1kur.authenticationservice.mapper.UserMapper;
import re1kur.authenticationservice.mq.publisher.EventPublisher;
import re1kur.authenticationservice.repository.UserRepository;
import re1kur.authenticationservice.service.AuthenticationService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultAuthenticationService implements AuthenticationService {
    private final JwtProvider jwtProvider;
    private final UserRepository repo;
    private final UserMapper mapper;
    private final EventPublisher publisher;
    private final BCryptPasswordEncoder encoder;

    @Override
    @Transactional
    public void register(UserPayload user) throws UserRegistrationException {
        if (repo.existsByEmail(user.email()))
            throw new UserRegistrationException("User with this email already exists.");
        User entity = mapper.write(user);
        repo.save(entity);
        publisher.publishUserRegistrationEvent(entity);
    }

    @Override
    public Token authenticate(UserPayload payload) throws UserAuthenticationException {
        User user = repo.findByEmail(payload.email()).orElseThrow(() -> new UserAuthenticationException("Invalid credentials."));
        if (!user.getIsEmailVerified())
            throw new UserAuthenticationException("User is not verified.");
        if (encoder.matches(payload.password(), user.getPassword())) return jwtProvider.provide(user);
        else throw new UserAuthenticationException("Invalid credentials.");
    }

    @Override
    public ResponseEntity<CheckVerificationResult> checkVerification(String email) {
        Optional<User> mayBeUser = repo.findByEmail(email);
        boolean isExist = mayBeUser.isPresent();
        return ResponseEntity.ok(new CheckVerificationResult(isExist, isExist ? mayBeUser.get().getIsEmailVerified() : null));
    }

    @Override
    public void verify(String email) throws UserVerificationException {
        User user = repo.findByEmail(email).orElseThrow(() -> new UserVerificationException("Invalid credentials."));
        user.setIsEmailVerified(true);
        repo.save(user);
    }
}
