package advisor.http.service;

import advisor.http.client.Request;
import advisor.model.api.categories.Categories;
import advisor.model.api.news.Albums;
import advisor.model.api.playlist.Playlist;
import io.vavr.control.Try;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.http.HttpResponse;

import static advisor.http.service.RequestServiceTestUtils.*;
import static io.vavr.control.Try.success;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestServiceTest {
  private RequestService service;
  @Mock private Request request;

  @BeforeEach
  void setUp() {
    service = new RequestService(request, "access token");
  }

  @Test
  @DisplayName("Should not null")
  void shouldNotNull() {
    assertNotNull(request);
    assertNotNull(service);
  }

  @Test
  @DisplayName("Should return success for getNews()")
  void shouldReturnSuccessForGetNews() {
    HttpResponse mockedResponse = Mockito.mock(HttpResponse.class);
    when(mockedResponse.body()).thenReturn(NEWS_RELEASES_JSON);
    when(request.makeHttpGetRequest(anyString(), anyString())).thenReturn(success(mockedResponse));

    Try<Albums> albums = service.getNews();
    assertTrue(albums.isSuccess());
  }

  @Test
  @DisplayName("Should return success for getFeatured()")
  void shouldReturnSuccessForGetFeatured() {
    HttpResponse mockedResponse = Mockito.mock(HttpResponse.class);
    when(mockedResponse.body()).thenReturn(FEATURED_PLAYLIST_JSON);
    when(request.makeHttpGetRequest(anyString(), anyString())).thenReturn(success(mockedResponse));

    Try<Playlist> featured = service.getFeatured();
    assertTrue(featured.isSuccess());
  }

  @Test
  @DisplayName("Should return success for getPlaylistCategory()")
  void shouldReturnSuccessForGetPlaylistCategory() {
    HttpResponse mockedResponse = Mockito.mock(HttpResponse.class);
    when(mockedResponse.body()).thenReturn(CATEGORIES_PLAYLIST_JSON);
    when(request.makeHttpGetRequest(anyString(), anyString())).thenReturn(success(mockedResponse));

    Try<Playlist> category = service.getPlaylistCategory("party");
    assertTrue(category.isSuccess());
    Try<Categories> topCategories = service.getTopCategories();
    assertTrue(topCategories.isSuccess());
  }
}
