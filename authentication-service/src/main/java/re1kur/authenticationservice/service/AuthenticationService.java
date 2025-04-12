package re1kur.authenticationservice.service;

import org.springframework.http.ResponseEntity;
import re1kur.authenticationservice.dto.ResultCheckVerification;
import re1kur.authenticationservice.dto.UserPayload;
import re1kur.authenticationservice.dto.UserWriteDto;
import re1kur.authenticationservice.exception.UserAuthenticationException;
import re1kur.authenticationservice.exception.UserRegistrationException;
import re1kur.authenticationservice.jwt.entity.Token;

public interface AuthenticationService {
    void register(UserWriteDto user) throws UserRegistrationException;

    Token authenticate(UserPayload user) throws UserAuthenticationException;

    ResponseEntity<ResultCheckVerification> checkVerification(String email);
}
