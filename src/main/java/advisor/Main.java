package advisor;

import advisor.args.Args;
import advisor.args.AccessTokenDetails;
import advisor.args.ServerDetails;
import advisor.args.SpotifyDashboardIDs;
import advisor.http.RequestUtil;
import advisor.http.client.Request;
import advisor.music.MusicAdvisor;

public class Main {
  public static void main(String[] args) {
    Args arg = new Args(args);
    ServerDetails details = arg.getServerDetails();
    RequestUtil.init(details);
    SpotifyDashboardIDs spotifyDashboardIDs = arg.getSpotifyDashboardIDs();
    AccessTokenDetails.init(spotifyDashboardIDs, details);
    new MusicAdvisor().start();
  }
}
