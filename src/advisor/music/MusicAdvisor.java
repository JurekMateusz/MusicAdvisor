package advisor.music;


import advisor.model.token.AccessToken;
import advisor.music.io.InputOutputHelper;
import advisor.music.task.auth.AuthenticateUserTask;
import advisor.music.task.main.MainAppTask;
import advisor.http.service.RequestService;

import java.io.IOException;
import java.util.Objects;

public class MusicAdvisor {
    private String userSpotifyCode = "";
    private InputOutputHelper ioHelper = new InputOutputHelper();
    private RequestService service = new RequestService();

    public void start() {
        AuthenticateUserTask task = new AuthenticateUserTask(ioHelper);
        task.execute();
        this.userSpotifyCode = task.getCode();

        ioHelper.printProcedureAccessToken();
        AccessToken accessToken;
        try {
            accessToken = service.getFirstAccessToken(userSpotifyCode);
        } catch (IOException | InterruptedException ex) {
            throw new IllegalStateException("Connection error: " + ex.getMessage());
        }
        if (!isAccessTokenCorrect(accessToken)) {
            throw new IllegalStateException("Fail to get access token form code.");
        }
        ioHelper.printAccessTokenAsJson(accessToken);
        ioHelper.printSuccessAuthentication();

        new MainAppTask(accessToken,ioHelper).execute();
    }

    private boolean isAccessTokenCorrect(AccessToken accessToken) {
        return Objects.nonNull(accessToken) &&
                hasData(accessToken) &&
                service.isAccessTokenWork(accessToken);

    }

    private boolean hasData(AccessToken aT) {
        return !(aT.getAccessToken().isEmpty() ||
                aT.getRefreshToken().isEmpty() ||
                aT.getTokenType().isEmpty() ||
                aT.getExpiresIn() == 0);
    }
}
