package re1kur.verificationservice.client;

import re1kur.verificationservice.exception.UserAlreadyVerifiedException;
import re1kur.verificationservice.exception.UserNotFoundException;

public interface AuthenticationClient {
    void checkVerification(String email) throws UserNotFoundException, UserAlreadyVerifiedException;
}
