package re1kur.authenticationservice.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import re1kur.authenticationservice.exception.UserAuthenticationException;
import re1kur.authenticationservice.exception.UserRegistrationException;
import re1kur.authenticationservice.exception.UserVerificationException;

@ControllerAdvice
public class DefaultControllerAdvice {

    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity<String> handleUserRegistrationException(UserRegistrationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not register user:\n%s".formatted(ex.getMessage()));
    }

    @ExceptionHandler(UserVerificationException.class)
    public ResponseEntity<String> handleUserVerificationException(UserVerificationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not verify user:\n%s".formatted(ex.getMessage()));
    }

    @ExceptionHandler(UserAuthenticationException.class)
    public ResponseEntity<String> handleUserAuthenticationException(UserAuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Could not authenticate user:\n%s".formatted(ex.getMessage()));
    }
}
