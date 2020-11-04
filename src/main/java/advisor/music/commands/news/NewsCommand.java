package advisor.music.commands.news;

import advisor.http.service.RequestService;
import advisor.model.api.news.Albums;
import advisor.model.view.Result;
import advisor.music.commands.Command;
import advisor.music.commands.DataObject;
import advisor.music.error.MusicAdvisorErrorHandling;
import advisor.view.Console;
import io.vavr.control.Try;
import lombok.Getter;

@Getter
public class NewsCommand extends Command implements DataObject {
  private Albums albums;

  public NewsCommand(RequestService service) {
    super(service);
  }

  @Override
  public boolean execute() {
    Try<Albums> albumsMonad = service.getNews();
    albumsMonad.onSuccess(
        albums -> {
          Result result = resultBuilder.createResultOf(albums);
          Console.log(result);
        });
    albumsMonad.onFailure(MusicAdvisorErrorHandling::handleError);
    albums = albumsMonad.get();
    return albumsMonad.isSuccess();
  }

  @Override
  public Albums get() {
    return albums;
  }
}
