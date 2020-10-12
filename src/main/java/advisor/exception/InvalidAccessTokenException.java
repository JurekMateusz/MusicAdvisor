package advisor.exception;

public class InvalidAccessTokenException extends RuntimeException {
    public static final String MESSAGE = "Can't made request to spotify because access token is invalid or has been expired.";

    public InvalidAccessTokenException() {
        super(MESSAGE);
    }
}
