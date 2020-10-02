package advisor.music.task.main;

import advisor.model.token.AccessToken;
import advisor.music.Category;
import advisor.music.Option;
import advisor.music.io.InputOutputHelper;
import advisor.music.task.MusicAdvisorTask;

public class MainAppTask implements MusicAdvisorTask {
    private AccessToken accessToken;
    private final InputOutputHelper ioHelper;
    private boolean isRunning = true;

    public MainAppTask(AccessToken accessToken, InputOutputHelper ioHelper) {
        this.accessToken = accessToken;
        this.ioHelper = ioHelper;
        // todo thread quarding setting new accessToken after expires old one;
    }

    @Override
    public void execute() {
        Option option;
        Category category;

        while (isRunning) {
            String input = ioHelper.readInput();
            option = parseOptionInput(input);

            switch (option) {
                case NEW:
                    ioHelper.printNewReleases();
                    break;
                case FEATURED:
                    ioHelper.printFeatured();
                    break;
                case CATEGORIES:
                    ioHelper.printCategories();
                    break;
                case PLAYLIST:
                    category = parseCategoryInput(input);//todo
                    ioHelper.printPlayListMood();
                    break;
                case EXIT:
                    turnOffApp();
                    ioHelper.printGoodbye();
                case UNKNOWN:
                    ioHelper.printIllegalArgumentMessage(input);
            }
        }
    }

    private void turnOffApp() {
        this.isRunning = false;
    }
}
