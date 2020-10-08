package advisor.music.lifecycle.main.task.tasks;

import advisor.model.api.categories.Category;
import advisor.model.api.playlist.Playlist;
import advisor.music.lifecycle.UserInput;
import advisor.music.lifecycle.main.task.InputTaskAbstract;

import java.io.IOException;
import java.util.Optional;

public class PlaylistTask extends InputTaskAbstract {
    @Override
    public void perform(String accessToken, UserInput input) throws IOException, InterruptedException {
        Category category;
        try {
            category = getCategoryId(input.getCategory());
        } catch (IllegalArgumentException ex) {
            System.out.println("Unknown category name.");
            return;
        }

        Playlist playlist = service.getPlaylistCategory(accessToken, category.getId());
        playlist.getSongs().forEach(System.out::println);
    }

    private Category getCategoryId(String category) throws IllegalArgumentException {
        Optional<Category> any = InputTaskAbstract.categories
                .stream()
                .filter(category1 -> category1.getName().equalsIgnoreCase(category))
                .findAny();
        return any.orElseThrow(IllegalArgumentException::new);
    }
}
