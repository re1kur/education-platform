package re1kur.authenticationservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import re1kur.authenticationservice.dto.UserAuthentication;
import re1kur.authenticationservice.dto.UserVerification;
import re1kur.authenticationservice.dto.UserWriteDto;
import re1kur.authenticationservice.entity.User;
import re1kur.authenticationservice.exception.UserAuthenticationException;
import re1kur.authenticationservice.exception.UserRegistrationException;
import re1kur.authenticationservice.exception.UserVerificationException;
import re1kur.authenticationservice.jwt.JwtProvider;
import re1kur.authenticationservice.jwt.entity.Token;
import re1kur.authenticationservice.mapper.UserMapper;
import re1kur.authenticationservice.repository.UserRepository;
import re1kur.authenticationservice.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultUserService implements UserService {
    private final JwtProvider jwtProvider;
    private final UserRepository repo;
    private final UserMapper mapper;

    @Override
    @Transactional
    public void register(UserWriteDto user) throws UserRegistrationException {
        if (repo.existsByEmail(user.getEmail()))
            throw new UserRegistrationException("The user with entered email already exists.");

        User entity = mapper.write(user);
        repo.save(entity);
    }

    @Override
    public Token authenticate(UserAuthentication user) throws UserAuthenticationException {
        Optional<User> mayBeUser = repo.findByEmailAndPassword(user.email(), user.password());
        if (mayBeUser.isPresent()) {
            User authUser = mayBeUser.get();
            if (!authUser.getIsEmailVerified())
                throw new UserAuthenticationException("This user's email is not verified.");
            return jwtProvider.provide(authUser);
        }
        throw new UserAuthenticationException("User not found");
    }

    @Override
    @Transactional
    public void verify(UserVerification user) throws UserVerificationException {
        Optional<User> mayBeUser = repo.findByEmail(user.email());
        if (mayBeUser.isPresent()) {
            User verifyUser = mayBeUser.get();
            if (verifyUser.getIsEmailVerified()) throw new UserVerificationException("This user already verified.");
            verifyUser.setIsEmailVerified(true);
            repo.save(verifyUser);
        }
        else throw new UserVerificationException("User not found");
    }
}
