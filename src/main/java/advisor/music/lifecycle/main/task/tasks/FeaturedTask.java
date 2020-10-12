package advisor.music.lifecycle.main.task.tasks;

import advisor.model.api.playlist.Playlist;
import advisor.model.view.Result;
import advisor.music.lifecycle.Task;
import advisor.music.lifecycle.UserInput;
import advisor.music.lifecycle.main.task.InputTaskAbstract;

import java.io.IOException;

public class FeaturedTask extends InputTaskAbstract {
    @Override
    public Result perform(String accessToken, UserInput input) throws IOException, InterruptedException {
        previousTask = Task.FEATURED;
        Playlist playlist = service.getFeatured(accessToken);
        updateOffsetInfo(playlist);
        return createResultOf(playlist);
    }
}
