package advisor.http.client;

import advisor.args.ServerDetails;
import advisor.exception.ContentNotFoundException;
import advisor.exception.InvalidAccessTokenException;
import advisor.exception.InvalidSpotifyCodeException;
import advisor.model.api.error.ErrorRoot;
import com.google.gson.Gson;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// todo thread safety ,templeMethod
public class Request {
  private static final int HTTP_NOT_FOUND = 404;
  private static final int HTTP_UNAUTHORIZED = 401;
  private static final int HTTP_OK = 200;
  private static String SPOTIFY_TOKEN_URI = "https://accounts.spotify.com/api/token";
  private static String API = "https://api.spotify.com/v1/browse";
  private static String NEWS_URL = API + "/new-releases?limit=";
  private static String FEATURED_PLAYLIST_URL = API + "/featured-playlists?limit=";
  private static String CATEGORIES_URL = API + "/categories";
  private static Request INSTANCE;
  private final HttpClient client;

  private Request() {
    this.client = HttpClient.newBuilder().build();
  }

  public static Request getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new Request();
    }
    return INSTANCE;
  }

  public String getFirstAccessToken(String contentToGetFirstAccessToken)
      throws IOException, InterruptedException, InvalidSpotifyCodeException {
    HttpRequest request =
        HttpRequest.newBuilder()
            .setHeader("Content-Type", MediaType.APPLICATION_FORM_URLENCODED)
            .uri(URI.create(SPOTIFY_TOKEN_URI))
            .POST(HttpRequest.BodyPublishers.ofString(contentToGetFirstAccessToken))
            .build();
    HttpResponse<String> result = client.send(request, HttpResponse.BodyHandlers.ofString());
    if (result.statusCode() != HTTP_OK) {
      throw new InvalidSpotifyCodeException();
    }
    return result.body();
  }

  public String getNew(String accessToken, int limit)
      throws IOException, InterruptedException, InvalidAccessTokenException {
    String authHeader = createAuthHeader(accessToken);
    //        return makeHttpGetRequestWith(authHeader, NEWS_URL+limit);
    return makeHttpGetRequestWith(authHeader, NEWS_URL);
  }

  public String getNew(String accessToken, String url)
      throws IOException, InterruptedException, InvalidAccessTokenException {
    String authHeader = createAuthHeader(accessToken);
    return makeHttpGetRequestWith(authHeader, url);
  }

  public String getFeatured(String accessToken, int limit)
      throws IOException, InterruptedException, InvalidAccessTokenException {
    String authHeader = createAuthHeader(accessToken);
    //        return makeHttpGetRequestWith(authHeader, FEATURED_PLAYLIST_URL + limit);
    return makeHttpGetRequestWith(authHeader, FEATURED_PLAYLIST_URL);
  }

  public String getFeatured(String accessToken, String url)
      throws IOException, InterruptedException, InvalidAccessTokenException {
    String authHeader = createAuthHeader(accessToken);
    return makeHttpGetRequestWith(authHeader, url);
  }

  public String getTopCategories(String accessToken, int limit)
      throws IOException, InterruptedException, InvalidAccessTokenException {
    String authHeader = createAuthHeader(accessToken);
    //        String url = CATEGORIES_URL + "?limit=" + limit;
    String url = CATEGORIES_URL;
    return makeHttpGetRequestWith(authHeader, url);
  }

  public String getTopCategories(String accessToken, String url)
      throws IOException, InterruptedException, InvalidAccessTokenException {
    String authHeader = createAuthHeader(accessToken);
    return makeHttpGetRequestWith(authHeader, url);
  }

  public String getPlaylistCategory(String accessToken, int limit, String categoryId)
      throws IOException, InterruptedException, InvalidAccessTokenException {
    String authHeader = createAuthHeader(accessToken);
    //        String url = createCategoryUrl(categoryId, limit);
    String url = CATEGORIES_URL + "/" + categoryId + "/playlists";
    return makeHttpGetRequestWith(authHeader, url);
  }

  public String getPlaylistCategory(String accessToken, String url, String categoryId)
      throws IOException, InterruptedException, InvalidAccessTokenException {
    String authHeader = createAuthHeader(accessToken);
    return makeHttpGetRequestWith(authHeader, url);
  }

  private String makeHttpGetRequestWith(String auth, String toUrl)
      throws IOException, InterruptedException, InvalidAccessTokenException {
    HttpRequest httpRequest =
        HttpRequest.newBuilder()
            .setHeader("Content-Type", MediaType.APPLICATION_JSON)
            .setHeader("Authorization", auth)
            .uri(URI.create(toUrl))
            .GET()
            .build();
    HttpResponse<String> result = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    validateResult(result);
    return result.body();
  }

  private void validateResult(HttpResponse<String> result) {
    int statusCode = result.statusCode();
    if (statusCode == HTTP_UNAUTHORIZED) {
      throw new InvalidAccessTokenException();
    }
    if (statusCode == HTTP_NOT_FOUND) {
      throw new ContentNotFoundException("Nothing found in: " + result.uri());
    }
    if (result.body().contains("error")) {
      ErrorRoot errorRoot = new Gson().fromJson(result.body(), ErrorRoot.class);
      throw new ContentNotFoundException(errorRoot.getError().getMessage());
    }
  }

  private String createAuthHeader(String accessToken) {
    return "Bearer " + accessToken;
  }

  private String createCategoryUrl(String categoryId, int limit) {
    return CATEGORIES_URL + "/" + categoryId + "/playlists?limit=" + limit;
  }

  public void init(ServerDetails details) {
    SPOTIFY_TOKEN_URI = details.getServerAccessPath() + "/api/token";
    API = details.getServerApiPath() + "/v1/browse";

    //        this.NEWS_URL = API + "/new-releases?limit=";
    //        this.FEATURED_PLAYLIST_URL = API + "/featured-playlists?limit=";
    //        this.CATEGORIES_URL = API + "/categories";
    String limit = "limit=" + details.getNumberOfEntriesInPage();
    NEWS_URL = API + "/new-releases?" + limit;
    FEATURED_PLAYLIST_URL = API + "/featured-playlists?" + limit;
    CATEGORIES_URL = API + "/categories";
  }
}
