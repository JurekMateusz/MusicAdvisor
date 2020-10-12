package advisor.exception;

public class InvalidSpotifyCodeException extends RuntimeException {
  private static final String MESSAGE = "Can't exchange spotify code to access token";

  public InvalidSpotifyCodeException() {
    super(MESSAGE);
  }
}
