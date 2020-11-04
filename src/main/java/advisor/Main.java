package advisor;

import advisor.args.AccessTokenDetails;
import advisor.args.Args;
import advisor.args.ServerDetails;
import advisor.args.SpotifyDashboardIDs;
import advisor.http.client.Request;
import advisor.http.service.RequestService;
import advisor.http.service.RequestUtil;
import advisor.music.MusicAdvisor;
import advisor.music.input.InputProvider;

import java.net.http.HttpClient;

public class Main {
  public static void main(String[] args) {
    Args arg = new Args(args);
    ServerDetails details = arg.getServerDetails();
    RequestUtil.init(details);

    SpotifyDashboardIDs spotifyDashboardIDs = arg.getSpotifyDashboardIDs();
    AccessTokenDetails.init(spotifyDashboardIDs, details);

    Request request = new Request(HttpClient.newBuilder().build());
    RequestService service = new RequestService(request);

    MusicAdvisor musicAdvisor = new MusicAdvisor(service, new InputProvider());
    musicAdvisor.start();
  }
}
