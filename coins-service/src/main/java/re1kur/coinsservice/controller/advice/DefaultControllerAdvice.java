package re1kur.coinsservice.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import re1kur.coinsservice.exception.NoSubjectClaimException;
import re1kur.coinsservice.exception.UserNotFoundException;

@ControllerAdvice
public class DefaultControllerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(NoSubjectClaimException.class)
    public ResponseEntity<String> handleNoSubjectClaimException(NoSubjectClaimException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }
}
