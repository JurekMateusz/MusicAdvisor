package advisor.http.service;

import advisor.model.api.categories.Categories;
import advisor.model.api.categories.CategoriesRoot;
import advisor.model.api.news.Albums;
import advisor.model.api.news.AlbumsRoot;
import advisor.model.api.playlist.Playlist;
import advisor.model.api.playlist.PlaylistRoot;
import advisor.model.token.AccessToken;
import com.google.gson.Gson;
import io.vavr.control.Try;

import java.net.http.HttpResponse;

class Mapper {
  private final Gson mapper = new Gson();

  Try<AccessToken> mapAccessToken(Try<HttpResponse<String>> response) {
    return response.map(HttpResponse::body).map(s -> mapper.fromJson(s, AccessToken.class));
  }

  Try<Albums> mapToAlbums(Try<HttpResponse<String>> response) {
    return response
        .map(HttpResponse::body)
        .map(s -> mapper.fromJson(s, AlbumsRoot.class))
        .map(AlbumsRoot::getAlbums);
  }

  Try<Playlist> mapToPlaylist(Try<HttpResponse<String>> response) {
    return response
        .map(HttpResponse::body)
        .map(s -> mapper.fromJson(s, PlaylistRoot.class))
        .map(PlaylistRoot::getPlaylists);
  }

  Try<Categories> mapToCategories(Try<HttpResponse<String>> response) {
    return response
        .map(HttpResponse::body)
        .map(s -> mapper.fromJson(s, CategoriesRoot.class))
        .map(CategoriesRoot::getCategories);
  }
}
