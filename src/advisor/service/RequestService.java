package advisor.service;

import advisor.httpclient.Request;
import advisor.model.token.AccessToken;

import java.io.IOException;

public class RequestService {
    private Request request = new Request();

    public AccessToken getFirstAccessToken(String spotifyCode) throws IOException, InterruptedException {
        return request.getAccessToken(spotifyCode);
    }

    public boolean isAccessTokenWork(AccessToken accessToken) {
        return true; //todo
    }
}
