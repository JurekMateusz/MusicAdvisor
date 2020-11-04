package advisor.music;

import advisor.http.service.RequestService;
import advisor.model.token.AccessToken;
import advisor.music.input.InputProvider;
import advisor.view.Console;

public class MusicAdvisor {
  private final RequestService service;
  private final InputProvider inputProvider;
  private AccessToken accessToken;

  public MusicAdvisor(RequestService service, InputProvider inputProvider) {
    this.service = service;
    this.inputProvider = inputProvider;
  }

  public void start() {
    AdvisorAuthorizeUser authorizer = new AdvisorAuthorizeUser(service, inputProvider);
    if (!authorizer.authorizeUser()) {
      Console.log("Can't authorize user");
      System.exit(-1);
    }
    accessToken = authorizer.getAccessToken();
    authorizer = null;
    new AdvisorMainStage(service, inputProvider, accessToken).start();
  }
}
