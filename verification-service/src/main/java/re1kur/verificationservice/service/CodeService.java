package re1kur.verificationservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import re1kur.verificationservice.dto.VerificationPayload;
import re1kur.verificationservice.exception.*;

public interface CodeService {
    ResponseEntity<String> generateCode(String email) throws UserNotFoundException, UserAlreadyVerifiedException, JsonProcessingException;

    ResponseEntity<String> verify(VerificationPayload payload) throws NotFoundCodeVerificationException, ExpiredCodeVerificationException, IncorrectCodeVerification, UserNotFoundException, UserAlreadyVerifiedException, UserVerificationException;
}
