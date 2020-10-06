package advisor.http.client;

import advisor.exception.ContentNotFoundException;
import advisor.exception.InvalidAccessTokenException;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Request {
    private static final int HTTP_NOT_FOUND = 404;
    private static final int HTTP_UNAUTHORIZED = 401;
    private static final String SPOTIFY_TOKEN_URI = "https://accounts.spotify.com/api/token";
    private static final String API = "https://api.spotify.com/v1/browse";
    private static final String NEWS_URL = API + "/new-releases?limit=";
    private static final String FEATURED_PLAYLIST_URL = API + "/featured-playlists?limit=";
    private static final String CATEGORIES_URL = API + "/categories";
    private HttpClient client;

    public Request() {
        this.client = HttpClient.newBuilder().build();
    }

    public String getAccessToken(String code) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .setHeader("Content-Type", MediaType.APPLICATION_FORM_URLENCODED)
                .uri(URI.create(SPOTIFY_TOKEN_URI))
                .POST(HttpRequest.BodyPublishers.ofString(code))
                .build();
        HttpResponse<String> result = client.send(request, HttpResponse.BodyHandlers.ofString());
        return result.body();
    }

    public String getNew(String accessToken, int limit) throws IOException, InterruptedException, InvalidAccessTokenException {
        String authHeader = createAuthHeader(accessToken);
        return makeHttpGetRequestWith(authHeader, NEWS_URL + limit);
    }

    public String getFeatured(String accessToken, int limit) throws IOException, InterruptedException, InvalidAccessTokenException {
        String authHeader = createAuthHeader(accessToken);
        return makeHttpGetRequestWith(authHeader, FEATURED_PLAYLIST_URL + limit);
    }

    public String getTopCategories(String accessToken, int limit) throws IOException, InterruptedException, InvalidAccessTokenException {
        String authHeader = createAuthHeader(accessToken);
        String url = CATEGORIES_URL + "?limit=" + limit;
        return makeHttpGetRequestWith(authHeader, url);
    }

    public String getPlaylistCategory(String accessToken, int limit, String categoryId) throws IOException, InterruptedException, InvalidAccessTokenException {
        String authHeader = createAuthHeader(accessToken);
        String url = createCategoryUrl(categoryId, limit);
        return makeHttpGetRequestWith(authHeader, url);
    }

    private String makeHttpGetRequestWith(String auth, String toUrl) throws IOException, InterruptedException, InvalidAccessTokenException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .setHeader("Content-Type", MediaType.APPLICATION_JSON)
                .setHeader("Authorization", auth)
                .uri(URI.create(toUrl))
                .GET()
                .build();
        HttpResponse<String> result = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        int statusCode = result.statusCode();
        if (statusCode == HTTP_UNAUTHORIZED) {
            throw new InvalidAccessTokenException();
        }
        if (statusCode == HTTP_NOT_FOUND) {
            throw new ContentNotFoundException("Nothing found in: " + toUrl);
        }
        return result.body();
    }

    private String createAuthHeader(String accessToken) {
        return "Bearer " + accessToken;
    }

    private String createCategoryUrl(String categoryId, int limit) {
        return CATEGORIES_URL + "/" + categoryId + "/playlists?limit=" + limit;
    }
}
