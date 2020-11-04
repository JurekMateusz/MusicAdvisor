package advisor.music;

import advisor.args.AccessTokenDetails;
import advisor.http.service.RequestService;
import advisor.model.token.AccessToken;
import advisor.music.commands.exit.ExitCommand;
import advisor.music.error.MusicAdvisorErrorHandling;
import advisor.music.input.Input;
import advisor.music.input.InputProvider;
import advisor.music.input.Task;
import advisor.server.ServerService;
import advisor.view.Console;
import io.vavr.control.Try;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import static advisor.music.input.Task.AUTH;
import static advisor.music.input.Task.EXIT;

@RequiredArgsConstructor(access = AccessLevel.MODULE)
public class AdvisorAuthorizeUser {
  private final String MESSAGE_AUTH = "Provide access for application. Type 'AUTH' command";
  @NonNull private final RequestService service;
  @NonNull private final InputProvider inputProvider;
  private AccessToken accessToken;

  public boolean authorizeUser() {
    String code = getUserSpotifyCode();
    Console.log("code received");
    Console.log("making http request for access_token...");
    if (retrieveAccessTokenFrom(code)) {
      Console.log("Success!");
      return true;
    }
    return false;
  }

  private String getUserSpotifyCode() {
    Console.log(MESSAGE_AUTH);
    while (true) {
      Input input = inputProvider.getUserInput();
      Task task = input.getTask();
      if (task == EXIT) {
        new ExitCommand().execute();
      }
      if (task == AUTH) {
        Console.log(AccessTokenDetails.getAuthorizeUserLink());
        Console.log("waiting for code...");
        openBrowserIfSupported();
        return new ServerService().getUserSpotifyCode();
      } else {
        Console.log(MESSAGE_AUTH);
      }
    }
  }

  private void openBrowserIfSupported() {
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
      try {
        Desktop.getDesktop().browse(new URI(AccessTokenDetails.getAuthorizeUserLink()));
      } catch (IOException | URISyntaxException e) {
        e.printStackTrace();
      }
    }
  }

  private boolean retrieveAccessTokenFrom(String code) {
    Try<AccessToken> tryToken = service.getFirstAccessToken(code);
    tryToken.onFailure(MusicAdvisorErrorHandling::handleError);
    accessToken = tryToken.get();
    checkAccessTokenWorking(accessToken);
    return true;
  }

  private void checkAccessTokenWorking(AccessToken accessToken) {
    if (!isAccessTokenCorrect(accessToken)) {
      throw new IllegalStateException("Can't make fulfill request with obtained access token");
    }
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

  public AccessToken getAccessToken() {
    return accessToken;
  }
}
