package advisor.music.lifecycle.main.task.tasks;

import advisor.model.view.Result;
import advisor.music.lifecycle.UserInput;
import advisor.music.lifecycle.main.task.InputTaskAbstract;

public class UnknownTask extends InputTaskAbstract {
    @Override
    public Result perform(String accessToken, UserInput input) {
        return Result.of("Dont recognize this input : " + input.getTask().name());
    }
}
