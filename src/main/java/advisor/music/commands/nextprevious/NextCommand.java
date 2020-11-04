package advisor.music.commands.nextprevious;

import advisor.http.service.RequestService;
import advisor.model.api.categories.Categories;
import advisor.model.api.news.Albums;
import advisor.model.api.playlist.Playlist;
import advisor.model.view.Result;
import advisor.music.commands.Command;
import advisor.music.commands.nextprevious.albums.AlbumsSeq;
import advisor.music.commands.nextprevious.categories.CategoriesSeq;
import advisor.music.commands.nextprevious.playlist.PlaylistSeq;
import advisor.view.Console;
import io.vavr.control.Option;
import lombok.Getter;

@Getter
public class NextCommand extends Command {
  private final Next next;

  public NextCommand(RequestService service, Albums albums) {
    super(null);
    next = new AlbumsSeq(service, albums);
  }

  public NextCommand(RequestService service, Playlist playlist) {
    super(null);
    next = new PlaylistSeq(service, playlist);
  }

  public NextCommand(RequestService service, Categories categories) {
    super(null);
    next = new CategoriesSeq(service, categories);
  }

  @Override
  public boolean execute() {
    Option<Result> result = next.next();
    result.peek(Console::log);
    return result.isDefined();
  }

  public Next getNext() {
    return next;
  }
}
