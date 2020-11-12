package advisor.music.guardian;

import advisor.http.service.RequestService;
import advisor.model.token.AccessToken;
import advisor.music.AdvisorMainStage;
import io.vavr.control.Try;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AccessTokenGuardian {
  private final AdvisorMainStage app;
  private final RequestService service;
  private AccessToken accessToken;

  public AccessTokenGuardian(
      AdvisorMainStage app, RequestService service, AccessToken accessToken) {
    this.app = app;
    this.service = service;
    this.accessToken = accessToken;
    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    executor.scheduleAtFixedRate(
        () -> {
          AccessToken newToken = getNewAccessToken();
          app.updateAccessToken(newToken);
          this.accessToken = newToken;
        },
        1,
        countSleepTime(),
        TimeUnit.SECONDS);
  }

  public AccessToken getNewAccessToken() {
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
