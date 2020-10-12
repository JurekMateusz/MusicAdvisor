package advisor.args;

import java.util.Objects;

import static org.springframework.util.StringUtils.hasText;

public class FirstAccessTokenDetails {
  private static final String GRANT_TYPE = "grant_type=authorization_code";
  private static final String redirectUri = "redirect_uri=http://localhost:8080";
  private static final String AMP = "&";
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

  public static String makeContent(String code) {
    assert spotifyDevKeysPresent() : "Client id and secret from spotify is empty";
    return GRANT_TYPE
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
}
