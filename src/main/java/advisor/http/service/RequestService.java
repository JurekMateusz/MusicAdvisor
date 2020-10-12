package advisor.http.service;

import advisor.args.FirstAccessTokenDetails;
import advisor.exception.InvalidAccessTokenException;
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

// todo
public class RequestService {
  private static final RequestService INSTANCE = new RequestService();
  private static final int DEFAULT_LIMIT = 7;

  private final Request request = Request.getInstance();
  private final Gson mapper = new Gson();

  public static RequestService getInstance() {
    return INSTANCE;
  }

  public AccessToken getFirstAccessToken(String spotifyCode)
      throws IOException, InterruptedException, InvalidSpotifyCodeException {
    String contentToGetFirstAccessToken = FirstAccessTokenDetails.makeContent(spotifyCode);
    String json = request.getFirstAccessToken(contentToGetFirstAccessToken);
    return mapper.fromJson(json, AccessToken.class);
  }

  public Albums getNews(String accessToken) throws IOException, InterruptedException {
    return getNews(accessToken, DEFAULT_LIMIT);
  }

  public Albums getNews(String accessToken, int limit) throws IOException, InterruptedException {
    String json = request.getNew(accessToken, limit);
    AlbumsRoot albums = mapper.fromJson(json, AlbumsRoot.class);
    return albums.getAlbums();
  }

  public Albums getNewsByUrl(String accessToken, String url)
      throws IOException, InterruptedException {
    String json = request.getNew(accessToken, url);
    AlbumsRoot albums = mapper.fromJson(json, AlbumsRoot.class);
    return albums.getAlbums();
  }

  public Playlist getFeatured(String accessToken) throws IOException, InterruptedException {
    return getFeatured(accessToken, DEFAULT_LIMIT);
  }

  public Playlist getFeatured(String accessToken, int limit)
      throws IOException, InterruptedException {
    String json = request.getFeatured(accessToken, limit);
    PlaylistRoot playlistRoot = mapper.fromJson(json, PlaylistRoot.class);
    return playlistRoot.getPlaylists();
  }

  public Playlist getPlaylistsByUrl(String accessToken, String url)
      throws IOException, InterruptedException {
    String json = request.getFeatured(accessToken, url);
    PlaylistRoot playlistRoot = mapper.fromJson(json, PlaylistRoot.class);
    return playlistRoot.getPlaylists();
  }

  public Categories getTopCategories(String accessToken) throws IOException, InterruptedException {
    return getTopCategories(accessToken, DEFAULT_LIMIT);
  }

  public Categories getTopCategories(String accessToken, int limit)
      throws IOException, InterruptedException {
    String json = request.getTopCategories(accessToken, limit);
    CategoriesRoot categoriesRoot = mapper.fromJson(json, CategoriesRoot.class);
    return categoriesRoot.getCategories();
  }

  public Categories getTopCategoriesByUrl(String accessToken, String url)
      throws IOException, InterruptedException {
    String json = request.getTopCategories(accessToken, url);
    CategoriesRoot categoriesRoot = mapper.fromJson(json, CategoriesRoot.class);
    return categoriesRoot.getCategories();
  }

  public Playlist getPlaylistCategory(String accessToken, String categoryId)
      throws IOException, InterruptedException {
    return getPlaylistCategory(accessToken, DEFAULT_LIMIT, categoryId);
  }

  public Playlist getPlaylistCategory(String accessToken, int limit, String categoryId)
      throws IOException, InterruptedException {
    String json = request.getPlaylistCategory(accessToken, limit, categoryId);
    PlaylistRoot playlist = mapper.fromJson(json, PlaylistRoot.class);
    return playlist.getPlaylists();
  }

  public boolean isAccessTokenWork(AccessToken accessToken) {
    try {
      request.getNew(accessToken.getAccessToken(), DEFAULT_LIMIT);
    } catch (InterruptedException | IOException e) {
      e.printStackTrace();
    } catch (InvalidAccessTokenException ex) {
      return false;
    }
    return true;
  }
}
