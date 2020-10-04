package advisor.music.task.auth;

import advisor.music.Option;
import advisor.music.io.InputOutputHelper;
import advisor.music.task.MusicAdvisorTask;
import advisor.server.ServerService;

import static advisor.music.Option.AUTH;
import static advisor.music.Option.EXIT;

public class AuthenticateUserTask implements MusicAdvisorTask {
    private static final String AUTHORIZE_USER_LINK = "https://accounts.spotify.com/authorize?" +
            "client_id=2ee3d9aa7be04620bbc2838939e84407" +
            "&redirect_uri=http://localhost:8080&response_type=code";
    private final InputOutputHelper ioHelper;
    private String code = "";

    public AuthenticateUserTask(InputOutputHelper ioHelper) {
        this.ioHelper = ioHelper;
    }

    @Override
    public void execute() {
        authenticateUser();
    }

    private void authenticateUser() {
        while (true) {
            String input = ioHelper.readInput();
            Option option = parseOptionInput(input);
            if (option == EXIT) {
                ioHelper.printGoodbye();
                System.exit(0);
            }
            if (option != AUTH) {
                ioHelper.printInfoAuthForUser();
                continue;
            }
            ioHelper.print(AUTHORIZE_USER_LINK);
            ioHelper.printWaitingForCode();
            code = new ServerService().getUserSpotifyCode();
            ioHelper.printCodeReceived();
            return;
        }
    }

    public String getCode() {
        return this.code;
    }
}
