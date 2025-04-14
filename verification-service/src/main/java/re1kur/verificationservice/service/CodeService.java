package re1kur.verificationservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import exception.*;
import org.springframework.http.ResponseEntity;
import payload.VerificationPayload;

public interface CodeService {
    ResponseEntity<String> generateCode(String email) throws JsonProcessingException, UserNotFoundException, UserAlreadyVerifiedException;

    ResponseEntity<String> verify(VerificationPayload payload) throws IncorrectCodeVerification, UserNotFoundException, UserAlreadyVerifiedException, UserVerificationException, NotFoundCodeVerificationException, ExpiredCodeVerificationException;
}
