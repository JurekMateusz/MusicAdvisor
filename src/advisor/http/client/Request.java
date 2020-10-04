package advisor.http.client;

import advisor.model.token.AccessToken;
import com.google.gson.Gson;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

//todo try to use decorator Gson
public class Request {
    private static final String CLIENT_ID = "client_id=2ee3d9aa7be04620bbc2838939e84407";
    private static final String CLIENT_SECRET = "client_secret=aea346f5479d4f7f955fbe683b3a6e47";
    private static final String GRANT_TYPE = "grant_type=authorization_code";
    private static final String REDIRECT_URI = "redirect_uri=http://localhost:8080";
    private final String SPOTIFY_TOKEN_URI = "https://accounts.spotify.com/api/token";
    private final String AMP = "&";
    private Gson mapper;
    private HttpClient client;

    public Request() {
        this.client = HttpClient.newBuilder().build();
        mapper = new Gson();
    }

    public AccessToken getAccessToken(String code) throws IOException, InterruptedException {
        String postContent = makeContent(code);
        String json = makePost(postContent);
        return mapper.fromJson(json, AccessToken.class);
    }

    private String makeContent(String code) {
        return GRANT_TYPE + AMP + "code=" + code + AMP + REDIRECT_URI + AMP + CLIENT_ID + AMP + CLIENT_SECRET;
    }

    private String makePost(String content) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .setHeader("Content-Type", MediaType.APPLICATION_FORM_URLENCODED)
                .uri(URI.create(SPOTIFY_TOKEN_URI))
                .POST(HttpRequest.BodyPublishers.ofString(content))
                .build();
        HttpResponse<String> result = client.send(request, HttpResponse.BodyHandlers.ofString());
        return result.body();
    }
}
