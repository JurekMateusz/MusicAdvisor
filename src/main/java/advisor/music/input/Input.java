package advisor.music.input;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Input {
  private final Task task;
  private final String category;

  public static Input of(Task task, String category) {
    return new Input(task, category);
  }
}
