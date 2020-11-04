package advisor.music.commands.exit;

import advisor.music.commands.Command;
import advisor.view.Console;

public class ExitCommand extends Command {

  public ExitCommand() {
    super(null);
  }

  @Override
  public boolean execute() {
    Console.log("---GOODBYE!---");
    System.exit(0);
    return true;
  }
}
