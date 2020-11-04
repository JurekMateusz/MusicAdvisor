package advisor.music.commands.unknown;

import advisor.model.view.Result;
import advisor.music.commands.Command;
import advisor.music.input.Input;
import advisor.view.Console;

public class UnknownCommand extends Command {
  private final Input input;

  public UnknownCommand(Input input) {
    super(null);
    this.input = input;
  }

  @Override
  public boolean execute() {
    Result result = Result.of("Dont recognize this input : " + input.getTask().getName());
    Console.log(result);
    return false;
  }
}
