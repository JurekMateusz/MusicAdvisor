package advisor.music.commands.playlist;

import advisor.http.service.RequestService;
import advisor.model.api.categories.Categories;
import advisor.model.api.categories.Category;
import advisor.model.api.playlist.Playlist;
import advisor.model.view.Result;
import advisor.music.commands.Command;
import advisor.music.commands.DataObject;
import advisor.music.error.MusicAdvisorErrorHandling;
import advisor.view.Console;
import io.vavr.control.Try;

import java.util.Optional;

public class PlaylistCommand extends Command implements DataObject {
  private Playlist playlist;
  private final String category;

  public PlaylistCommand(RequestService service, String category) {
    super(service);
    this.category = category;
  }

  @Override
  public boolean execute() {
    Optional<Category> categoryOpt = findCategory();
    if (categoryOpt.isEmpty()) {
      Result result = Result.of("Can't find id for category: " + category);
      Console.log(result);
      return false;
    }
    Category category = categoryOpt.get();
    Try<Playlist> playlistMonad = service.getPlaylistCategory(category.getId());
    playlistMonad.onSuccess(
        playlist -> {
          Result result = resultBuilder.createResultOf(playlist);
          Console.log(result);
        });
    playlistMonad.onFailure(MusicAdvisorErrorHandling::handleError);
    playlist = playlistMonad.get();
    return playlistMonad.isSuccess();
  }

  private Optional<Category> findCategory() {
    Try<Categories> allCategories = service.getAllCategories();
    if (allCategories.isFailure()) return Optional.empty();
    return allCategories.get().getCategories().stream()
        .filter(category -> category.getName().equalsIgnoreCase(this.category))
        .filter(category -> !category.getName().equalsIgnoreCase("top lists"))
        .findAny();
  }

  @Override
  public Object get() {
    return playlist;
  }
}
