package advisor.music;

import advisor.exception.InvalidAccessTokenException;
import advisor.http.service.RequestService;
import advisor.model.token.AccessToken;
import advisor.music.lifecycle.auth.AuthenticateUserLifecycle;
import advisor.music.lifecycle.main.MainAppLifecycle;

import java.io.IOException;
import java.util.Objects;

public class MusicAdvisor {
  private String userSpotifyCode = "";
  private final RequestService service = RequestService.getInstance();

  public void start() {
    AuthenticateUserLifecycle task = new AuthenticateUserLifecycle();
    task.execute();
    this.userSpotifyCode = task.getCode();

    System.out.println("making http request for access_token...");
    AccessToken accessToken;
    try {
      accessToken = service.getFirstAccessToken(userSpotifyCode);
    } catch (IOException | InterruptedException ex) {
      throw new IllegalStateException("Connection error: " + ex.getMessage());
    } catch (InvalidAccessTokenException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
    if (!isAccessTokenCorrect(accessToken)) {
      throw new IllegalStateException("Fail to get access token form code.");
    }
    System.out.println("Success!");
    new MainAppLifecycle(accessToken).execute();
  }

  private boolean isAccessTokenCorrect(AccessToken accessToken) {
    return Objects.nonNull(accessToken)
        && hasData(accessToken)
        && service.isAccessTokenWork(accessToken);
  }

  private boolean hasData(AccessToken aT) {
    return !(aT.getAccessToken().isEmpty()
        || aT.getRefreshToken().isEmpty()
        || aT.getTokenType().isEmpty()
        || aT.getExpiresIn() == 0);
  }
}
