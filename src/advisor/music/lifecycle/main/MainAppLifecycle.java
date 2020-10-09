package advisor.music.lifecycle.main;

import advisor.exception.ContentNotFoundException;
import advisor.exception.InvalidAccessTokenException;
import advisor.model.token.AccessToken;
import advisor.music.lifecycle.MusicAdvisorLifecycle;
import advisor.music.lifecycle.Task;
import advisor.music.lifecycle.UserInput;
import advisor.music.lifecycle.main.task.InputTaskAbstract;
import advisor.music.lifecycle.main.task.TaskPerformerFactory;

import java.io.IOException;

public class MainAppLifecycle implements MusicAdvisorLifecycle {
    private AccessToken accessToken;
    private boolean isRunning = true;

    public MainAppLifecycle(AccessToken accessToken) {
        this.accessToken = accessToken;
        // todo thread guarding setting new accessToken after expires old one;
    }

    @Override
    public void execute() {
        while (isRunning) {
            UserInput userInput = getUserInput();

            if (userInput.getTask() == Task.EXIT) {
                System.out.println("---GOODBYE!---");
                turnOffApp();
            }

            String accessToken = this.accessToken.getAccessToken();
            InputTaskAbstract performer = TaskPerformerFactory.get(userInput.getTask());
            try {
                performer.perform(accessToken, userInput);
            } catch (InterruptedException | IOException e) {
                System.out.println("Connection error");
                e.printStackTrace();
            } catch (InvalidAccessTokenException | ContentNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void turnOffApp() {
        this.isRunning = false;
    }
}
