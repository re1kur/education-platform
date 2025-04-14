package exception;

public class NotEnoughCoinsOnBalanceException extends Exception {
    public NotEnoughCoinsOnBalanceException(String message) {
        super(message);
    }
}
