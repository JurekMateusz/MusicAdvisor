package advisor.music.commands.featured;

import advisor.http.service.RequestService;
import advisor.model.api.playlist.Playlist;
import advisor.model.view.Result;
import advisor.music.commands.Command;
import advisor.music.commands.DataObject;
import advisor.music.error.MusicAdvisorErrorHandling;
import advisor.view.Console;
import io.vavr.control.Try;

public class FeaturedCommand extends Command implements DataObject {
  private Playlist playlists;

  public FeaturedCommand(RequestService service) {
    super(service);
  }

  @Override
  public boolean execute() {
    Try<Playlist> playlistMonad = service.getFeatured();
    playlistMonad.onSuccess(
        playlist -> {
          Result result = resultBuilder.createResultOf(playlist);
          Console.log(result);
        });
    playlistMonad.onFailure(MusicAdvisorErrorHandling::handleError);
    playlists = playlistMonad.get();
    return playlistMonad.isSuccess();
  }

  @Override
  public Object get() {
    return playlists;
  }
}
