package advisor.args;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class SpotifyDashboardIDs {
  private final String clientID;
  private final String clientSecret;

  public static SpotifyDashboardIDs of(String clientId, String clientSecret) {
    return new SpotifyDashboardIDs(clientId, clientSecret);
  }
}
