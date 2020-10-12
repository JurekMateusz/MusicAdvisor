package advisor.music.lifecycle.auth;

import advisor.args.FirstAccessTokenDetails;
import advisor.music.lifecycle.MusicAdvisorLifecycle;
import advisor.music.lifecycle.Task;
import advisor.music.lifecycle.UserInput;
import advisor.server.ServerService;

import static advisor.music.lifecycle.Task.AUTH;
import static advisor.music.lifecycle.Task.EXIT;

public class AuthenticateUserLifecycle implements MusicAdvisorLifecycle {
  private String code = "";

  @Override
  public void execute() {
    authenticateUser();
  }

  private void authenticateUser() {
    while (true) {
      UserInput userInput = getUserInput();
      Task task = userInput.getTask();
      if (task == EXIT) {
        System.out.println("---GOODBYE!---");
        System.exit(0);
      }
      if (task != AUTH) {
        System.out.println("Please, provide access for application.");
        continue;
      }
      System.out.println(FirstAccessTokenDetails.getAuthorizeUserLink());
      System.out.println("waiting for code...");
      code = new ServerService().getUserSpotifyCode();
      System.out.println("code received");
      return;
    }
  }

  public String getCode() {
    return this.code;
  }
}
