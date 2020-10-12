package advisor.music.lifecycle.main.task.tasks;

import advisor.exception.ContentNotFoundException;
import advisor.model.view.Result;
import advisor.music.lifecycle.UserInput;
import advisor.music.lifecycle.main.task.InputTaskAbstract;

import java.io.IOException;
import java.util.Objects;

public class NextTask extends InputTaskAbstract {
    @Override
    public Result perform(String accessToken, UserInput input) throws IOException, InterruptedException, ContentNotFoundException, InterruptedException {
//        if (Objects.isNull(offsetInfo)) {
//            return Result.of("You don't made any action previously");
//        }
//        if (Objects.isNull(offsetInfo.getNext())) {
//            return Result.of("No more pages.");
//        }
        String url = offsetInfo.getNext();
        lastPage += 1;
        return takePageFromUrl(accessToken, url);
    }
}
