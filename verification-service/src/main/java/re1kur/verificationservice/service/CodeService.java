package re1kur.verificationservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import exception.*;
import payload.VerificationPayload;

public interface CodeService {
    void generateCode(String email) throws JsonProcessingException, UserNotFoundException, UserAlreadyVerifiedException;

    void verify(VerificationPayload payload) throws IncorrectCodeVerification, UserNotFoundException, UserAlreadyVerifiedException, UserVerificationException, NotFoundCodeVerificationException, ExpiredCodeVerificationException;
}
