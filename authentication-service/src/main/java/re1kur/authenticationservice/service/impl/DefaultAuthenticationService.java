package re1kur.authenticationservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import re1kur.authenticationservice.dto.ResultCheckVerification;
import re1kur.authenticationservice.dto.UserPayload;
import re1kur.authenticationservice.dto.UserWriteDto;
import re1kur.authenticationservice.entity.User;
import re1kur.authenticationservice.exception.UserAuthenticationException;
import re1kur.authenticationservice.exception.UserRegistrationException;
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
    public void register(UserWriteDto user) throws UserRegistrationException {
        if (repo.existsByEmail(user.getEmail()))
            throw new UserRegistrationException("User with this email already exists.");
        User entity = mapper.write(user);
        repo.save(entity);
        publisher.publishUserRegistrationEvent(entity.getEmail());
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
    public ResponseEntity<ResultCheckVerification> checkVerification(String email) {
        Optional<User> mayBeUser = repo.findByEmail(email);
        boolean isExist = mayBeUser.isPresent();
        return ResponseEntity.ok(new ResultCheckVerification(isExist, isExist ? mayBeUser.get().getIsEmailVerified() : null));
    }
}
