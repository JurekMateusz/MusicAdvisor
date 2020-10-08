package advisor.music.lifecycle.main.task.tasks;

import advisor.model.api.news.Albums;
import advisor.music.lifecycle.UserInput;
import advisor.music.lifecycle.main.task.InputTaskAbstract;

import java.io.IOException;

public class NewsTask extends InputTaskAbstract {
    @Override
    public void perform(String accessToken, UserInput input) throws IOException, InterruptedException {
        Albums albums = service.getNews(accessToken);

        albums.getSongs().forEach(System.out::println);
    }
}
