package advisor.args;

import java.util.Objects;

import static org.springframework.util.StringUtils.hasText;

public class AccessTokenDetails {
  private static final String GRANT_TYPE_AUTHORIZATION_CODE = "grant_type=authorization_code";
  private static final String GRANT_TYPE_REFRESH_TOKEN = "grant_type=refresh_token";
  private static final String redirectUri = "redirect_uri=http://localhost:8080";
  private static final String AMP = "&";
  private static final String scopes = "user-read-recently-played";
  private static String authorizeUserLink;
  private static String clientID;
  private static String clientSecret;

  public static void init(SpotifyDashboardIDs dashboardIDs, ServerDetails serverDetails) {
    assert isDataSpotifyPresent(dashboardIDs) : "Empty spotify keys";
    assert isDataServerPresent(serverDetails) : "Data server incomplete";

    clientID = "client_id=" + dashboardIDs.getClientID();
    clientSecret = "client_secret=" + dashboardIDs.getClientSecret();
    authorizeUserLink =
        serverDetails.getServerAccessPath()
            + "/authorize?"
            + clientID
            + AMP
            + "scope="
            + scopes
            + AMP
            + redirectUri
            + "&response_type=code";
  }

  private static boolean isDataSpotifyPresent(SpotifyDashboardIDs iDs) {
    return dataPresent(iDs) && hasText(iDs.getClientID()) && hasText(iDs.getClientSecret());
  }

  private static boolean isDataServerPresent(ServerDetails sD) {
    return dataPresent(sD)
        && hasText(sD.getServerAccessPath())
        && hasText(sD.getServerApiPath())
        && sD.getNumberOfEntriesInPage() > 0;
  }

  private static boolean dataPresent(Object object) {
    return Objects.nonNull(object);
  }

  public static String makeContentForFirstToken(String code) {
    assert spotifyDevKeysPresent() : "Client id and secret from spotify is empty";
    return GRANT_TYPE_AUTHORIZATION_CODE
        + AMP
        + "code="
        + code
        + AMP
        + redirectUri
        + AMP
        + clientID
        + AMP
        + clientSecret;
  }

  public static String getAuthorizeUserLink() {
    return authorizeUserLink;
  }

  private static boolean spotifyDevKeysPresent() {
    return Objects.nonNull(clientID)
        && !clientID.isEmpty()
        && Objects.nonNull(clientSecret)
        && !clientSecret.isEmpty();
  }

  public static String makeContentForNewToken(String refreshToken) {
    return GRANT_TYPE_REFRESH_TOKEN
        + AMP
        + "refresh_token="
        + refreshToken
        + AMP
        + clientID
        + AMP
        + clientSecret;
  }
}
