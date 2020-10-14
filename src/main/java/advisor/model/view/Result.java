package advisor.model.view;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Result {
  private final String output;

  public static Result of(String output) {
    return new Result(output);
  }

  public static Result empty() {
    return new Result("");
  }
}
