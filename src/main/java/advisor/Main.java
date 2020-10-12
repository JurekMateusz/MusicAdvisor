package advisor;

import advisor.args.Args;
import advisor.args.FirstAccessTokenDetails;
import advisor.args.ServerDetails;
import advisor.args.SpotifyDashboardIDs;
import advisor.http.client.Request;
import advisor.music.MusicAdvisor;

public class Main {
  public static void main(String[] args) {
    Args arg = new Args(args);
    ServerDetails details = arg.getServerDetails();
    feedRequestSingletonServerParameter(details);
    SpotifyDashboardIDs spotifyDashboardIDs = arg.getSpotifyDashboardIDs();
    FirstAccessTokenDetails.init(spotifyDashboardIDs, details);
    new MusicAdvisor().start();
  }

  private static void feedRequestSingletonServerParameter(ServerDetails details) {
    Request request = Request.getInstance();
    request.init(details);
  }
}
