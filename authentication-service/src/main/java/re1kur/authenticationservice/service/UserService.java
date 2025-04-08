package re1kur.authenticationservice.service;

import re1kur.authenticationservice.dto.UserAuthentication;
import re1kur.authenticationservice.dto.UserVerification;
import re1kur.authenticationservice.dto.UserWriteDto;
import re1kur.authenticationservice.exception.UserAuthenticationException;
import re1kur.authenticationservice.exception.UserRegistrationException;
import re1kur.authenticationservice.exception.UserVerificationException;
import re1kur.authenticationservice.jwt.entity.Token;

public interface UserService {
    void register(UserWriteDto user) throws UserRegistrationException;

    Token authenticate(UserAuthentication user) throws UserAuthenticationException;

    void verify(UserVerification user) throws UserVerificationException;
}
