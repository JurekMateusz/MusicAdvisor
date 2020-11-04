package advisor.music.commands.nextprevious.categories;

import advisor.http.service.RequestService;
import advisor.model.api.categories.Categories;
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
public class CategoriesSeq implements Next, Previous, DataObject {
  public final RequestService service;
  private Categories categories;

  @Override
  public Option<Result> next() {
    String url = categories.getNext();
    return getResults(url);
  }

  @Override
  public Option<Result> previous() {
    String url = categories.getPrevious();
    return getResults(url);
  }

  private Option<Result> getResults(String url) {
    if (Objects.isNull(url)) return Option.of(Result.of("No more pages."));
    Try<Categories> topCategoriesByUrl = service.getTopCategoriesByUrl(url);
    topCategoriesByUrl.onFailure(MusicAdvisorErrorHandling::handleError);
    this.categories = topCategoriesByUrl.get();
    return topCategoriesByUrl.map(new ResultBuilder()::createResultOf).toOption();
  }

  @Override
  public Object get() {
    return categories;
  }
}
