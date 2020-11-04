package advisor.music.commands.info;

import advisor.model.view.Result;
import advisor.music.commands.Command;
import advisor.view.Console;

public class InfoCommand extends Command {
  private final String sep = System.lineSeparator();

  public InfoCommand() {
    super(null);
  }

  @Override
  public boolean execute() {
    String message =
        "COMMENDS:"
            + sep
            + " 'CATEGORIES' \t\t\t -> GET AVAILABLE CATEGORIES PAGE"
            + sep
            + " 'FEATURED' \t\t\t -> GET PAGE FEATURED SONGS"
            + sep
            + " 'PLAYLIST YOUR_CATEGORY' \t\t -> GET PAGE SONGS FORM SPECIFIC CATEGORY. EXAMPLE: 'PLAYLIST PARTY'"
            + sep
            + " 'NEXT' \t\t\t -> GET NEXT PAGE"
            + sep
            + " 'PREV' \t\t\t ->GET PREVIOUS PAGE"
            + sep
            + " 'INFO'"
            + sep;
    Console.log(Result.of(message));
    return false;
  }
}
