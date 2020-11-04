package advisor.music.error;

public class MusicAdvisorErrorHandling {
  static void handleError() {}

  public static void handleError(Throwable throwable) {
    throw new IllegalStateException("Error - implement error handler ");
  }
}
