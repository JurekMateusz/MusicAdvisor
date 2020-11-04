package advisor.music.commands;

import advisor.music.commands.nextprevious.NextCommand;
import advisor.music.commands.nextprevious.PreviousCommand;

import java.util.Objects;

public class CommandHistory {
  private Command command;

  public void push(Command command) {
    this.command = command;
  }

  public Object getDataFromLast() {
    if (command instanceof NextCommand) {
      return ((DataObject) ((NextCommand) (command)).getNext()).get();
    }
    if (command instanceof PreviousCommand) {
      return ((DataObject) ((PreviousCommand) (command)).getPrevious()).get();
    }
    return ((DataObject) (command)).get();
  }

  public boolean isEmpty() {
    return Objects.isNull(command);
  }
}
