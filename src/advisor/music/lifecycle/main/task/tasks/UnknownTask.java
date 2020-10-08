package advisor.music.lifecycle.main.task.tasks;

import advisor.music.lifecycle.UserInput;
import advisor.music.lifecycle.main.task.InputTaskAbstract;

public class UnknownTask extends InputTaskAbstract {
    @Override
    public void perform(String accessToken, UserInput input) {
        System.out.println("Dont recognize this input : " + input.getTask());
    }
}
