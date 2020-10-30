package advisor.http.service;

import advisor.args.AccessTokenDetails;
import advisor.exception.InvalidSpotifyCodeException;
import advisor.http.client.Request;
import advisor.model.api.categories.Categories;
import advisor.model.api.categories.CategoriesRoot;
import advisor.model.api.news.Albums;
import advisor.model.api.news.AlbumsRoot;
import advisor.model.api.playlist.Playlist;
import advisor.model.api.playlist.PlaylistRoot;
import advisor.model.token.AccessToken;
import com.google.gson.Gson;

import java.io.IOException;

import static advisor.http.RequestUtil.*;

public class RequestService {
  private static final RequestService INSTANCE = new RequestService();
  private static final int DEFAULT_LIMIT = 7;

  private final Request request = new Request();
  private final Gson mapper = new Gson();

  public RequestService(){
    
  }
  public static RequestService getInstance() {
    return INSTANCE;
  }

  public AccessToken getFirstAccessToken(String spotifyCode)
      throws IOException, InterruptedException, InvalidSpotifyCodeException {
    String contentToGetFirstAccessToken = AccessTokenDetails.makeContentForFirstToken(spotifyCode);
    String json = request.getAccessToken(contentToGetFirstAccessToken, SPOTIFY_TOKEN_URI);
    return mapper.fromJson(json, AccessToken.class);
  }

  public AccessToken getNewAccessToken(String refreshToken)
      throws IOException, InterruptedException {
    String contentToGetFirstAccessToken = AccessTokenDetails.makeContentForNewToken(refreshToken);
    String json = request.getAccessToken(contentToGetFirstAccessToken, SPOTIFY_TOKEN_URI);
    AccessToken accessToken = mapper.fromJson(json, AccessToken.class);
    accessToken.setRefreshToken(refreshToken);
    return accessToken;
  }

  public Albums getNews(String accessToken) throws IOException, InterruptedException {
    return getNews(accessToken, DEFAULT_LIMIT);
  }

  public Albums getNews(String accessToken, int limit) throws IOException, InterruptedException {
    String json = "";
    request.makeHttpGetRequest(accessToken,NEWS_URL);
    AlbumsRoot albums = mapper.fromJson(json, AlbumsRoot.class);
    return albums.getAlbums();
  }

  public Albums getNewsByUrl(String accessToken, String url)
      throws IOException, InterruptedException {
    String json = "";
    request.makeHttpGetRequest(accessToken, url);
    AlbumsRoot albums = mapper.fromJson(json, AlbumsRoot.class);
    return albums.getAlbums();
  }

  public Playlist getFeatured(String accessToken) throws IOException, InterruptedException {
    return getFeatured(accessToken, DEFAULT_LIMIT);
  }

  public Playlist getFeatured(String accessToken, int limit)
      throws IOException, InterruptedException {
    String json ="";
    request.makeHttpGetRequest(accessToken, FEATURED_PLAYLIST_URL);
    PlaylistRoot playlistRoot = mapper.fromJson(json, PlaylistRoot.class);
    return playlistRoot.getPlaylists();
  }

  public Playlist getPlaylistsByUrl(String accessToken, String url)
      throws IOException, InterruptedException {
    String json = "";
    request.makeHttpGetRequest(accessToken, url);
    PlaylistRoot playlistRoot = mapper.fromJson(json, PlaylistRoot.class);
    return playlistRoot.getPlaylists();
  }

  public Categories getTopCategories(String accessToken) throws IOException, InterruptedException {
    return getTopCategories(accessToken, DEFAULT_LIMIT);
  }

  public Categories getTopCategories(String accessToken, int limit)
      throws IOException, InterruptedException {
    String url = CATEGORIES_URL;
    String json = "";
    request.makeHttpGetRequest(accessToken, url);
    CategoriesRoot categoriesRoot = mapper.fromJson(json, CategoriesRoot.class);
    return categoriesRoot.getCategories();
  }

  public Categories getTopCategoriesByUrl(String accessToken, String url)
      throws IOException, InterruptedException {
    String json = "";
    request.makeHttpGetRequest(accessToken, url);
    CategoriesRoot categoriesRoot = mapper.fromJson(json, CategoriesRoot.class);
    return categoriesRoot.getCategories();
  }

  public Playlist getPlaylistCategory(String accessToken, String categoryId)
      throws IOException, InterruptedException {
    return getPlaylistCategory(accessToken, DEFAULT_LIMIT, categoryId);
  }

  public Playlist getPlaylistCategory(String accessToken, int limit, String categoryId)
      throws IOException, InterruptedException {
    String url = CATEGORIES_URL + "/" + categoryId + "/playlists";
    String json = "";
    request.makeHttpGetRequest(accessToken, url);
    PlaylistRoot playlist = mapper.fromJson(json, PlaylistRoot.class);
    return playlist.getPlaylists();
  }

  public boolean isAccessTokenWork(AccessToken accessToken) {
//    try {
//      request.getNew(accessToken.getAccessToken(), DEFAULT_LIMIT);
//    } catch (InterruptedException | IOException e) {
//      e.printStackTrace();
//    } catch (InvalidAccessTokenException ex) {
//      return false;
//    }
//    return true;
    return false;
  }

  private String createCategoryUrl(String categoryId, int limit) {
    return CATEGORIES_URL + "/" + categoryId + "/playlists?limit=" + limit;
  }
}
