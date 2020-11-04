package advisor.music.commands.nextprevious.albums;

import advisor.http.service.RequestService;
import advisor.model.api.news.Albums;
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
public class AlbumsSeq implements Next, Previous, DataObject {
  public final RequestService service;
  public Albums albums;

  @Override
  public Option<Result> next() {
    String url = albums.getNext();
    return getResult(url);
  }

  @Override
  public Option<Result> previous() {
    String url = albums.getPrevious();
    return getResult(url);
  }

  private Option<Result> getResult(String url) {
    if (Objects.isNull(url)) return Option.of(Result.of("No more pages."));
    Try<Albums> albums = service.getNewsByUrl(url);
    albums.onFailure(MusicAdvisorErrorHandling::handleError);
    this.albums = albums.get();
    return albums.map(new ResultBuilder()::createResultOf).toOption();
  }

  @Override
  public Albums get() {
    return albums;
  }
}
