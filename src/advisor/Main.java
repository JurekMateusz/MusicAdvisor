package advisor;

import advisor.args.Args;
import advisor.args.ServerDetails;
import advisor.http.client.Request;
import advisor.music.MusicAdvisor;
import advisor.music.lifecycle.main.task.InputTaskAbstract;
import advisor.music.lifecycle.main.task.tasks.UnknownTask;

public class Main {
    public static void main(String[] args) {
        Args arg = new Args(args);
        ServerDetails details = arg.getServerDetails();
        feedRequestSingletonServerParameter(details);
        //todo method needed only to pass test in 5 stage. Server in tests dont behave like spotify API.
        new UnknownTask().setDetails(details);
        new MusicAdvisor().start();
    }

    private static void feedRequestSingletonServerParameter(ServerDetails details) {
        Request request = Request.getInstance();
        request.init(details);
    }
}
