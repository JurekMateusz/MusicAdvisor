package advisor.music.lifecycle.auth;

import advisor.music.lifecycle.Task;
import advisor.music.lifecycle.UserInput;
import advisor.music.lifecycle.MusicAdvisorLifecycle;
import advisor.server.ServerService;

import static advisor.music.lifecycle.Task.AUTH;
import static advisor.music.lifecycle.Task.EXIT;

public class AuthenticateUserLifecycle implements MusicAdvisorLifecycle {
    private static final String AUTHORIZE_USER_LINK = "https://accounts.spotify.com/authorize?" +
            "client_id=2ee3d9aa7be04620bbc2838939e84407" +
            "&redirect_uri=http://localhost:8080&response_type=code";
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
            System.out.println(AUTHORIZE_USER_LINK);
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
