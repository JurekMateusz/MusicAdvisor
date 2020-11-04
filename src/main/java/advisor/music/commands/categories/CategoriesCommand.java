package advisor.music.commands.categories;

import advisor.http.service.RequestService;
import advisor.model.api.categories.Categories;
import advisor.model.view.Result;
import advisor.music.commands.Command;
import advisor.music.commands.DataObject;
import advisor.music.error.MusicAdvisorErrorHandling;
import advisor.view.Console;
import io.vavr.control.Try;
import lombok.Getter;

@Getter
public class CategoriesCommand extends Command implements DataObject {
  private Categories categories;

  public CategoriesCommand(RequestService service) {
    super(service);
  }

  @Override
  public boolean execute() {
    Try<Categories> categoriesMonad = service.getTopCategories();
    categoriesMonad.onSuccess(
        categories -> {
          Result result = resultBuilder.createResultOf(categories);
          Console.log(result);
        });
    categoriesMonad.onFailure(MusicAdvisorErrorHandling::handleError);
    categories = categoriesMonad.get();
    return categoriesMonad.isSuccess();
  }

  @Override
  public Categories get() {
    return categories;
  }
}
