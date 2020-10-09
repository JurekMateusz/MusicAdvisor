package advisor;

import advisor.args.Args;
import advisor.args.ServerDetails;
import advisor.http.client.Request;
import advisor.music.MusicAdvisor;

public class Main {
    public static void main(String[] args) {
        Args arg = new Args(args);
        ServerDetails details = arg.getServerDetails();
        feedRequestSingletonServerParameter(details);
        new MusicAdvisor().start();
    }

    private static void feedRequestSingletonServerParameter(ServerDetails details) {
        Request request = Request.getInstance();
        request.init(details);
    }
}
