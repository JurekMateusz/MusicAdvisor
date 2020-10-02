package advisor.music.task.auth;

import advisor.music.Option;
import advisor.music.io.InputOutputHelper;
import advisor.music.task.MusicAdvisorTask;
import advisor.server.Server;

import java.util.concurrent.Semaphore;

import static advisor.music.Option.AUTH;
import static advisor.music.Option.EXIT;

public class AuthenticateUserTask implements MusicAdvisorTask {
    private static final String AUTHORIZE_USER_LINK = "https://accounts.spotify.com/authorize?" +
            "client_id=2ee3d9aa7be04620bbc2838939e84407" +
            "&redirect_uri=http://localhost:8080&response_type=code";
    private final InputOutputHelper ioHelper;
    private String code = "";
    private Server server;
    private Semaphore semaphore;

    public AuthenticateUserTask(InputOutputHelper ioHelper) {
        this.ioHelper = ioHelper;
        semaphore = new Semaphore(1);
        this.server = new Server(this, semaphore);
    }

    @Override
    public void execute() {
        server.start();

        authenticateUser();

        server.stop();
    }

    private void authenticateUser() {
        while (true) {
            String input = ioHelper.readInput();
            Option option = parseOptionInput(input);
            if (option == EXIT) {
                ioHelper.printGoodbye();
                server.stop();
                System.exit(0);
            }
            if (option != AUTH) {
                ioHelper.printInfoAuthForUser();
                continue;
            }
            ioHelper.print(AUTHORIZE_USER_LINK);
            ioHelper.printWaitingForCode();
            stopThisExecutionUntilUserAuthenticateYourselfInSpotify();
            ioHelper.printCodeReceived();
            return;
        }
    }

    private void stopThisExecutionUntilUserAuthenticateYourselfInSpotify() {
        try {
            semaphore.acquire(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
