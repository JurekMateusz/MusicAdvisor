package advisor.music.lifecycle.main.tokenguardian;

import advisor.http.service.RequestService;
import advisor.model.token.AccessToken;
import advisor.music.lifecycle.main.MainAppLifecycle;

import java.io.IOException;

public class AccessTokenGuardian implements Runnable {
  private final MainAppLifecycle mainAppLifecycle;
  private final RequestService service;

  public AccessTokenGuardian(MainAppLifecycle mainAppLifecycle) {
    this.mainAppLifecycle = mainAppLifecycle;
    service = RequestService.getInstance();
  }

  @Override
  public void run() {
    while (true) {
      long sleepTime = countSleepTime();
      sleep(sleepTime);
      String refreshToken = getRefreshToken();
      AccessToken newToken = getAccessToken(refreshToken);
      mainAppLifecycle.setAccessToken(newToken);
    }
  }

  private AccessToken getAccessToken(String refreshToken) {
    AccessToken accessToken = null;
    try {
      accessToken = service.getNewAccessToken(refreshToken);
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
    return accessToken;
  }

  private long countSleepTime() {
    AccessToken accessToken = mainAppLifecycle.getAccessToken();
    return accessToken.getExpiresIn();
  }

  private void sleep(long sleepTime) {
    try {
      Thread.sleep(sleepTime);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private String getRefreshToken() {
    return mainAppLifecycle.getAccessToken().getRefreshToken();
  }
}
