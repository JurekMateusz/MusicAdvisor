package advisor.music.commands.nextprevious.playlist;

import advisor.http.service.RequestService;
import advisor.model.api.playlist.Playlist;
import advisor.model.view.Result;
import advisor.model.view.ResultBuilder;
import advisor.music.commands.DataObject;
import advisor.music.commands.nextprevious.Next;
import advisor.music.commands.nextprevious.Previous;
import advisor.music.error.MusicAdvisorErrorHandling;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
public class PlaylistSeq implements Next, Previous, DataObject {
  public final RequestService service;
  private Playlist playlist;

  @Override
  public Option<Result> next() {
    String url = playlist.getNext();
    return getResults(url);
  }

  @Override
  public Option<Result> previous() {
    String url = playlist.getPrevious();
    return getResults(url);
  }

  private Option<Result> getResults(String url) {
    if (Objects.isNull(url)) return Option.of(Result.of("No more pages."));
    Try<Playlist> playlistsMonad = service.getPlaylistsByUrl(url);
    playlistsMonad.onFailure(MusicAdvisorErrorHandling::handleError);
    this.playlist = playlistsMonad.get();
    return playlistsMonad.map(new ResultBuilder()::createResultOf).toOption();
  }

  @Override
  public Object get() {
    return playlist;
  }
}
