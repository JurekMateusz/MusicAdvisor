package advisor.music.commands;

import advisor.http.service.RequestService;
import advisor.model.view.ResultBuilder;

public abstract class Command {
  protected RequestService service;

  protected ResultBuilder resultBuilder = new ResultBuilder();

  public Command(RequestService service) {
    this.service = service;
  }

  public abstract boolean execute();
}
