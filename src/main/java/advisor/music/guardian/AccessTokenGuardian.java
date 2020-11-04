package advisor.music.guardian;

import advisor.http.service.RequestService;
import advisor.model.token.AccessToken;
import advisor.music.AdvisorMainStage;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccessTokenGuardian implements Runnable {
  private final AdvisorMainStage app;
  private final RequestService service;
  private AccessToken accessToken;

  @Override
  public void run() {
    while (true) {
      long sleepTime = countSleepTime();
      sleep(sleepTime);
      AccessToken newToken = getNewAccessToken();
      app.updateAccessToken(newToken);
      this.accessToken = newToken;
    }
  }

  private AccessToken getNewAccessToken() {
    Try<AccessToken> newAccessToken = service.getNewAccessToken(accessToken.getRefreshToken());
    if (newAccessToken.isSuccess()) {
      return newAccessToken.get();
    }
    throw new IllegalStateException("Can't get new access token");
  }

  private long countSleepTime() {
    return accessToken.getExpiresIn() - 5;
  }

  private void sleep(long sleepTime) {
    try {
      Thread.sleep(sleepTime);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
