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
public class PreviousCommand extends Command {
  private final Previous previous;

  public PreviousCommand(RequestService service, Albums albums) {
    super(null);
    previous = new AlbumsSeq(service, albums);
  }

  public PreviousCommand(RequestService service, Playlist playlist) {
    super(null);
    previous = new PlaylistSeq(service, playlist);
  }

  public PreviousCommand(RequestService service, Categories categories) {
    super(null);
    previous = new CategoriesSeq(service, categories);
  }

  @Override
  public boolean execute() {
    Option<Result> result = previous.previous();
    result.peek(Console::log);
    return result.isDefined();
  }

  public Previous getPrevious() {
    return previous;
  }
}
