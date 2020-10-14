package advisor.music.lifecycle.input;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserInput {
  private Task task;
  private String category;

  public static UserInput of(Task task, String category) {
    return new UserInput(task, category);
  }
}
