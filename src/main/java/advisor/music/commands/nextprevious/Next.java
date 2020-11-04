package advisor.music.commands.nextprevious;

import advisor.model.view.Result;
import io.vavr.control.Option;

public interface Next {
  Option<Result> next();
}
