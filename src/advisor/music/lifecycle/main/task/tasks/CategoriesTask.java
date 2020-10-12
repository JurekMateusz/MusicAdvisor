package advisor.music.lifecycle.main.task.tasks;

import advisor.model.api.categories.Categories;
import advisor.model.view.Result;
import advisor.music.lifecycle.Task;
import advisor.music.lifecycle.UserInput;
import advisor.music.lifecycle.main.task.InputTaskAbstract;

import java.io.IOException;

public class CategoriesTask extends InputTaskAbstract {
    @Override
    public Result perform(String accessToken, UserInput input) throws IOException, InterruptedException {
        previousTask = Task.CATEGORIES;
        Categories categories = service.getTopCategories(accessToken);
        lastPage = 0;
        InputTaskAbstract.topCategoriesByUrl = categories;//todo
        InputTaskAbstract.categories = categories.getCategories();
        updateOffsetInfo(categories);
        return createResultOf(categories);
    }
}
