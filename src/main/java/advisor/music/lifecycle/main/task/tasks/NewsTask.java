package advisor.music.lifecycle.main.task.tasks;

import advisor.model.api.news.Albums;
import advisor.model.view.Result;
import advisor.music.lifecycle.Task;
import advisor.music.lifecycle.UserInput;
import advisor.music.lifecycle.main.task.InputTaskAbstract;

import java.io.IOException;

public class NewsTask extends InputTaskAbstract {
    @Override
    public Result perform(String accessToken, UserInput input) throws IOException, InterruptedException {
        previousTask = Task.NEW;
        Albums albums = service.getNews(accessToken);
        updateOffsetInfo(albums);
        return createResultOf(albums);
    }
}
