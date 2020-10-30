package advisor.http.client;

import advisor.exception.ContentNotFoundException;
import advisor.exception.InvalidAccessTokenException;
import advisor.exception.InvalidSpotifyCodeException;
import advisor.model.api.error.ErrorRoot;
import com.google.gson.Gson;
import io.vavr.control.Try;

import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Request {
  private static final int HTTP_NOT_FOUND = 404;
  private static final int HTTP_UNAUTHORIZED = 401;
  private static final int HTTP_OK = 200;
  private final HttpClient client;

  public Request(HttpClient httpClient) {
    this.client = httpClient;
  }

  public Try<HttpResponse<String>> getAccessToken(String contentToGetFirstAccessToken, String url) {
    HttpRequest request =
        HttpRequest.newBuilder()
            .setHeader("Content-Type", MediaType.APPLICATION_FORM_URLENCODED)
            .uri(URI.create(url))
            .POST(HttpRequest.BodyPublishers.ofString(contentToGetFirstAccessToken))
            .build();
    return Try.of(
        () -> {
          HttpResponse<String> resp = client.send(request, HttpResponse.BodyHandlers.ofString());
          validateContentWithAccessToken(resp);
          return resp;
        });
  }

  private void validateContentWithAccessToken(HttpResponse<String> resp)
      throws InvalidSpotifyCodeException {
    if (resp.statusCode() != HTTP_OK) {
      throw new InvalidSpotifyCodeException();
    }
  }

  public Try<HttpResponse<String>> makeHttpGetRequest(String accessToken, String toUrl) {
    String authHeader = createAuthHeader(accessToken);
    HttpRequest httpRequest =
        HttpRequest.newBuilder()
            .setHeader("Content-Type", MediaType.APPLICATION_JSON)
            .setHeader("Authorization", authHeader)
            .uri(URI.create(toUrl))
            .GET()
            .build();
    return Try.of(
        () -> {
          HttpResponse<String> resp =
              client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
          validateResult(resp);
          return resp;
        });
  }

  private void validateResult(HttpResponse<String> response)
      throws InvalidAccessTokenException, ContentNotFoundException {
    int statusCode = response.statusCode();
    if (statusCode == HTTP_UNAUTHORIZED) {
      throw new InvalidAccessTokenException();
    }
    if (statusCode == HTTP_NOT_FOUND) {
      throw new ContentNotFoundException("Nothing found in: " + response.uri());
    }
    if (response.body().contains("error")) {
      ErrorRoot errorRoot = new Gson().fromJson(response.body(), ErrorRoot.class);
      throw new ContentNotFoundException(errorRoot.getError().getMessage());
    }
  }

  private String createAuthHeader(String accessToken) {
    return "Bearer " + accessToken;
  }
}
