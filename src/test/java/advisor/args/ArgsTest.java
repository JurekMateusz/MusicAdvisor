package advisor.args;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ArgsTest {
  private final String api = "http://127.0.0.1:1222";
  private final String resource = "http://127.0.0.1:12112";
  private final String numberOnPage = "10";
  private final String clientId = "9324yhewkjsyhr37irkds";
  private final String clientSecret = "4385irg3kwecusdfdsr32";

  @Test
  @DisplayName("Should server details be initiated with default value")
  void shouldServerDetailsBeInitiatedWithDefaultValue() {
    ServerDetails serverDetailsExpected =
        ServerDetails.builder()
            .serverApiPath(Args.DEFAULT_API_SERVER_PATH)
            .serverAccessPath(Args.DEFAULT_AUTHORIZATION_SERVER_PATH)
            .numberOfEntriesInPage(Args.DEFAULT_NUMBER_OF_ENTRIES_ON_PAGE)
            .build();
    String[] args = {};
    testServerDetails(serverDetailsExpected, args);
  }

  private void testServerDetails(ServerDetails serverDetailsExpected, String[] args) {
    Args testedObject = new Args(args);
    ServerDetails result = testedObject.getServerDetails();
    assertEquals(serverDetailsExpected, result);
  }

  @Test
  @DisplayName("Should server details be initiated with program arguments")
  void shouldServerDetailsBeInitiatedWithProgramArguments() {
    ServerDetails expected =
        ServerDetails.builder()
            .serverApiPath(api)
            .serverAccessPath(resource)
            .numberOfEntriesInPage(Integer.parseInt(numberOnPage))
            .build();
    String[] args = {
      Args.ARG_NAME_AUTHORIZATION_SERVER,
      resource,
      Args.ARG_NAME_API_SEVER,
      api,
      Args.ARG_NAME_PAGE,
      numberOnPage
    };
    testServerDetails(expected, args);
  }

  @Test
  @DisplayName("Should server details be initiated with api and auth link only")
  void shouldServerDetailsBeInitiatedWithApiAndAuthLinkOnly() {
    ServerDetails expected =
        ServerDetails.builder()
            .serverApiPath(api)
            .serverAccessPath(resource)
            .numberOfEntriesInPage(Args.DEFAULT_NUMBER_OF_ENTRIES_ON_PAGE)
            .build();
    String[] args = {
      Args.ARG_NAME_AUTHORIZATION_SERVER,
      resource,
      Args.ARG_NAME_API_SEVER,
      api,
      Args.ARG_NAME_PAGE,
      String.valueOf(Args.DEFAULT_NUMBER_OF_ENTRIES_ON_PAGE)
    };
    testServerDetails(expected, args);
  }

  @Test
  @DisplayName("Should parse user dashboard client id and client secret")
  void shouldParseUserDashboardClientIdAndClientSecret() {
    SpotifyDashboardIDs expected = SpotifyDashboardIDs.of(clientId, clientSecret);
    String[] args = {
      Args.ARG_CLIENT_ID, clientId,
      Args.ARG_CLIENT_SECRET, clientSecret
    };
    Args testedObject = new Args(args);
    SpotifyDashboardIDs result = testedObject.getSpotifyDashboardIDs();
    assertEquals(expected, result);
  }

  @Test
  @DisplayName("Should throw error when try get spotify dashboard IDs with now initialization args")
  void shouldThrowErrorWhenTryGetSpotifyDashboardIDsWithNowInitialization() {
    Args testedObject = new Args();
    assertThrows(IllegalStateException.class, testedObject::getSpotifyDashboardIDs);
  }

  @Test
  @DisplayName(
      "Should throw error when try get spotify dashboard IDs with initialization only one field")
  void shouldThrowErrorWhenTryGetSpotifyDashboardIDsWithInitializationOneField() {
    String[] args = {Args.ARG_CLIENT_ID, clientId};
    Args testedObject = new Args(args);
    assertThrows(IllegalStateException.class, testedObject::getSpotifyDashboardIDs);
  }

  @Test
  @DisplayName("Should throw error when try initiate dashboard IDs with same ids")
  void shouldThrowErrorWhenTryInitiateDashboardIDsWithSameIds() {
    String[] args = {Args.ARG_CLIENT_ID, clientId, Args.ARG_CLIENT_SECRET, clientId};

    assertThrows(IllegalArgumentException.class, () -> new Args(args));
  }

  @Test
  @DisplayName("Should throw error when program args repeat themselves")
  void shouldThrowErrorWhenProgramArgsRepeatThemselves() {
    String[] args = {
      Args.ARG_NAME_API_SEVER,
      Args.ARG_NAME_API_SEVER,
      Args.ARG_CLIENT_ID,
      Args.ARG_CLIENT_SECRET,
      clientId
    };

    assertThrows(IllegalArgumentException.class, () -> new Args(args));
  }
}
