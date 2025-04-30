package exception;

public class ATMOutOfCashException extends RuntimeException {
    public ATMOutOfCashException(String message) {
        super(message);
    }
}
