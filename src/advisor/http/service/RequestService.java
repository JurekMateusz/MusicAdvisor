package advisor.http.service;

import advisor.exception.InvalidAccessTokenException;
import advisor.http.client.Request;
import advisor.model.api.categories.CategoriesRoot;
import advisor.model.api.news.AlbumsRoot;
import advisor.model.api.playlist.PlaylistRoot;
import advisor.model.token.AccessToken;
import com.google.gson.Gson;

import java.io.IOException;

public class RequestService {
    private static final int DEFAULT_LIMIT = 7;
    private static final String CLIENT_ID = "client_id=2ee3d9aa7be04620bbc2838939e84407";
    private static final String CLIENT_SECRET = "client_secret=aea346f5479d4f7f955fbe683b3a6e47";
    private static final String GRANT_TYPE = "grant_type=authorization_code";
    private static final String REDIRECT_URI = "redirect_uri=http://localhost:8080";
    private final String AMP = "&";

    private Request request = new Request();
    private Gson mapper = new Gson();

    public AccessToken getFirstAccessToken(String spotifyCode) throws IOException, InterruptedException {
        String contentToGetFirstAccessToken = makeContent(spotifyCode);
        String json = request.getAccessToken(contentToGetFirstAccessToken);
        return mapper.fromJson(json, AccessToken.class);
    }

    private String makeContent(String code) {
        return GRANT_TYPE + AMP + "code=" + code + AMP + REDIRECT_URI + AMP + CLIENT_ID + AMP + CLIENT_SECRET;
    }

    public AlbumsRoot getNews(String accessToken) throws IOException, InterruptedException {
        return getNews(accessToken, DEFAULT_LIMIT);
    }

    public AlbumsRoot getNews(String accessToken, int limit) throws IOException, InterruptedException {
        String json = request.getNew(accessToken, limit);
        AlbumsRoot albums = mapper.fromJson(json, AlbumsRoot.class);
        return albums;
    }

    public PlaylistRoot getFeatured(String accessToken) throws IOException, InterruptedException {
        return getFeatured(accessToken, DEFAULT_LIMIT);
    }

    public PlaylistRoot getFeatured(String accessToken, int limit) throws IOException, InterruptedException {
        String json = request.getFeatured(accessToken, limit);
        PlaylistRoot playlistRoot = mapper.fromJson(json, PlaylistRoot.class);
        return playlistRoot;
    }

    public CategoriesRoot getTopCategories(String accessToken) throws IOException, InterruptedException {
        return getTopCategories(accessToken, DEFAULT_LIMIT);
    }

    public CategoriesRoot getTopCategories(String accessToken, int limit) throws IOException, InterruptedException {
        String json = request.getTopCategories(accessToken, limit);
        CategoriesRoot categoriesRoot = mapper.fromJson(json, CategoriesRoot.class);
        return categoriesRoot;
    }

    public PlaylistRoot getPlaylistCategory(String accessToken, String categoryId) throws IOException, InterruptedException {
        return getPlaylistCategory(accessToken, DEFAULT_LIMIT, categoryId);
    }

    public PlaylistRoot getPlaylistCategory(String accessToken, int limit, String categoryId) throws IOException, InterruptedException {
        String json = request.getPlaylistCategory(accessToken, limit, categoryId);
        PlaylistRoot playlist = mapper.fromJson(json, PlaylistRoot.class);
        return playlist;
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
