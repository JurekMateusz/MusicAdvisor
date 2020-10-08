package advisor.music.lifecycle.main.task.tasks;

import advisor.model.api.playlist.Playlist;
import advisor.music.lifecycle.UserInput;
import advisor.music.lifecycle.main.task.InputTaskAbstract;

import java.io.IOException;

public class FeaturedTask extends InputTaskAbstract {
    @Override
    public void perform(String accessToken, UserInput input) throws IOException, InterruptedException {
        Playlist playlist = service.getFeatured(accessToken);
        playlist.getSongs().forEach(System.out::println);
    }
}
