package advisor.http.client;

import advisor.exception.InvalidAccessTokenException;
import io.vavr.control.Try;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestTest {
  private Request request;
  @Mock private HttpClient httpClient;

  @BeforeEach
  void setUp() {
    request = new Request(httpClient);
  }

  @Test
  @DisplayName("shouldNotNull")
  void shouldNotNull() {
    assertNotNull(httpClient);
    assertNotNull(request);
  }

  @SneakyThrows
  @Test
  @DisplayName("Should throw error")
  void shouldThrowError() {
    Class<IOException> error = IOException.class;
    when(httpClient.send(any(), any())).thenThrow(error);
    Try<HttpResponse<String>> result = request.makeHttpGetRequest("test", "http://localhost:8080/");

    assertTrue(result.isFailure());
    assertEquals(result.getCause().toString(), error.getName());
  }

  @SneakyThrows
  @Test
  @DisplayName("Should throw invalid access token for bad access token")
  void shouldThrowInvalidAccessTokenForBadAccessToken() {
    HttpResponse mockedResponse = Mockito.mock(HttpResponse.class);
    when(mockedResponse.statusCode()).thenReturn(401);
    when(httpClient.send(any(), any())).thenReturn(mockedResponse);

    Try<HttpResponse<String>> result = request.makeHttpGetRequest("test", "http://localhost:8080/");

    assertTrue(result.isFailure());
    assertEquals(result.getCause().getClass(), InvalidAccessTokenException.class);
  }

  @SneakyThrows
  @Test
  @DisplayName("Should throw content not found if status code 404")
  void shouldThrowContentNotFoundIfStatusCode404() {
    HttpResponse mockedResponse = Mockito.mock(HttpResponse.class);
    when(mockedResponse.statusCode()).thenReturn(401);
    when(httpClient.send(any(), any())).thenReturn(mockedResponse);

    Try<HttpResponse<String>> result = request.makeHttpGetRequest("test", "http://localhost:8080/");

    assertTrue(result.isFailure());
    assertEquals(result.getCause().getClass(), InvalidAccessTokenException.class);
  }
}
