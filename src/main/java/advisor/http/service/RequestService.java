package advisor.http.service;

import advisor.args.AccessTokenDetails;
import advisor.http.client.Request;
import advisor.model.api.categories.Categories;
import advisor.model.api.news.Albums;
import advisor.model.api.playlist.Playlist;
import advisor.model.token.AccessToken;
import io.vavr.control.Try;

import java.net.http.HttpResponse;

import static advisor.http.service.RequestUtil.*;

public class RequestService {
  private final Request request;
  private final Mapper mapper = new Mapper();
  private String accessToken;

  public RequestService(Request request) {
    this.request = request;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public Try<AccessToken> getFirstAccessToken(String spotifyCode) {
    String firstAccessTokenContent = AccessTokenDetails.makeContentForFirstToken(spotifyCode);
    Try<HttpResponse<String>> accessToken =
        request.getAccessToken(firstAccessTokenContent, SPOTIFY_TOKEN_URI);
    return mapper.mapAccessToken(accessToken);
  }

  public Try<AccessToken> getNewAccessToken(String refreshToken) {
    String newAccessTokenContent = AccessTokenDetails.makeContentForNewToken(refreshToken);
    Try<HttpResponse<String>> accessToken =
        request.getAccessToken(newAccessTokenContent, SPOTIFY_TOKEN_URI);
    return mapper
        .mapAccessToken(accessToken)
        .map(
            accessTok -> {
              accessTok.setRefreshToken(refreshToken);
              return accessTok;
            });
  }

  private Try<HttpResponse<String>> getHttpResponse(String url) {
    return request.makeHttpGetRequest(accessToken, url);
  }

  public Try<Albums> getNews() {
    Try<HttpResponse<String>> httpResponse = getHttpResponse(NEWS_URL);
    return mapper.mapToAlbums(httpResponse);
  }

  public Try<Albums> getNewsByUrl(String url) {
    Try<HttpResponse<String>> response = getHttpResponse(url);
    return mapper.mapToAlbums(response);
  }

  public Try<Playlist> getFeatured() {
    Try<HttpResponse<String>> response = getHttpResponse(FEATURED_PLAYLIST_URL);
    return mapper.mapToPlaylist(response);
  }

  public Try<Playlist> getPlaylistsByUrl(String url) {
    Try<HttpResponse<String>> response = getHttpResponse(url);
    return mapper.mapToPlaylist(response);
  }

  public Try<Playlist> getPlaylistCategory(String categoryId) {
    String url = getCategoriesUrlByID(categoryId);
    Try<HttpResponse<String>> response = getHttpResponse(url);
    return mapper.mapToPlaylist(response);
  }

  public Try<Categories> getAllCategories() {
    Try<HttpResponse<String>> response = getHttpResponse(FULL_CATEGORIES_URL);
    return mapper.mapToCategories(response);
  }

  public Try<Categories> getTopCategories() {
    Try<HttpResponse<String>> response = getHttpResponse(CATEGORIES_URL);
    return mapper.mapToCategories(response);
  }

  public Try<Categories> getTopCategoriesByUrl(String url) {
    Try<HttpResponse<String>> response = getHttpResponse(url);
    return mapper.mapToCategories(response);
  }

  public boolean isAccessTokenWork(AccessToken accessToken) {
    this.accessToken = accessToken.getAccessToken();
    Try<Albums> news = getNews();
    return news.isSuccess();
  }
}
