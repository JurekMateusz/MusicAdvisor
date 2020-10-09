package advisor.music.lifecycle.main.task.tasks;

import advisor.model.api.categories.Categories;
import advisor.model.api.categories.Category;
import advisor.model.api.playlist.Playlist;
import advisor.music.lifecycle.UserInput;
import advisor.music.lifecycle.main.task.InputTaskAbstract;

import java.io.IOException;
import java.util.Optional;

public class PlaylistTask extends InputTaskAbstract {
    @Override
    public void perform(String accessToken, UserInput input) throws IOException, InterruptedException {
        if (categories.isEmpty()) {
            updateCategories(accessToken);
        }

        Category category;
        try {
            category = getCategoryId(input.getCategory());
        } catch (IllegalArgumentException ex) {
            System.out.println("Unknown category name.");
            return;
        }

        Playlist playlist = service.getPlaylistCategory(accessToken, category.getId());//todo
        playlist.getSongs().forEach(System.out::println);
    }

    private void updateCategories(String accessToken) throws IOException, InterruptedException {
        Categories topCategories = service.getTopCategories(accessToken, 100);
        categories = topCategories.getCategories();
    }

    private Category getCategoryId(String category) throws IllegalArgumentException {
        Optional<Category> any = InputTaskAbstract.categories
                .stream()
                .filter(category1 -> category1.getName().equalsIgnoreCase(category))
                .filter(category1 -> !category1.getName().equalsIgnoreCase("toplists"))
                .findAny();
        return any.orElseThrow(IllegalArgumentException::new);
    }
}
