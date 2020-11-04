package advisor.music.commands.nextprevious;

import advisor.model.view.Result;
import advisor.music.commands.Command;
import advisor.view.Console;

public class NoNextPreviousCommand extends Command {
  public NoNextPreviousCommand() {
    super(null);
  }

  @Override
  public boolean execute() {
    Result result = Result.of("Can't made command because you dont type any command before");
    Console.log(result);
    return false;
  }
}
