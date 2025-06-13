package re1kur.authenticationservice.service;

import exception.UserVerificationException;
import org.springframework.http.ResponseEntity;
import dto.CheckVerificationResult;
import payload.UserPayload;
import exception.UserAuthenticationException;
import exception.UserRegistrationException;
import re1kur.authenticationservice.jwt.entity.Token;

public interface AuthenticationService {
    void register(UserPayload user) throws UserRegistrationException;

    Token authenticate(UserPayload user) throws UserAuthenticationException;

    ResponseEntity<CheckVerificationResult> checkVerification(String email);

    void verify(String email) throws UserVerificationException;
}
