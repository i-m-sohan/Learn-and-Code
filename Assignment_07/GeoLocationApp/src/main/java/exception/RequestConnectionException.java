package exception;

public class RequestConnectionException extends RuntimeException {
    public RequestConnectionException(String message) {
        super(message);
    }
}
