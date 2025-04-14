package re1kur.verificationservice.client;


import exception.UserAlreadyVerifiedException;
import exception.UserNotFoundException;

public interface AuthenticationClient {
    void checkVerification(String email) throws UserNotFoundException, UserAlreadyVerifiedException;
}
