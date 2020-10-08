package advisor.music.lifecycle.main.task.tasks;

import advisor.model.api.categories.Categories;
import advisor.music.lifecycle.UserInput;
import advisor.music.lifecycle.main.task.InputTaskAbstract;

import java.io.IOException;

public class CategoriesTask extends InputTaskAbstract {
    @Override
    public void perform(String accessToken, UserInput input) throws IOException, InterruptedException {
        Categories categories = service.getTopCategories(accessToken);
        InputTaskAbstract.categories = categories.getCategories();
        categories.getCategories().forEach(System.out::println);
    }
}
