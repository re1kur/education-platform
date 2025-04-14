package exception;

public class ExpiredCodeVerificationException extends Exception {
    public ExpiredCodeVerificationException(String message) {
        super(message);
    }
}
