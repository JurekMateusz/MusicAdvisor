package advisor.music.input;

import java.util.Scanner;

import static advisor.music.input.Task.UNKNOWN;

public class InputProvider {
  private final Scanner scanner = new Scanner(System.in);

  public Input getUserInput() {
    String rawInput = scanner.nextLine();
    String input = rawInput.trim();
    String[] split = input.split(" +");
    if (split.length == 0) {
      return Input.of(UNKNOWN, "unknown");
    }
    Task task = parseTaskInput(split[0]);
    String category = getCategory(split);

    return Input.of(task, category);
  }

  private Task parseTaskInput(String input) {
    try {
      return Task.valueOf(input.toUpperCase());
    } catch (IllegalArgumentException ex) {
      return UNKNOWN.set(input);
    }
  }

  private String getCategory(String[] splittedImput) {
    String result = "";
    try {
      result = splittedImput[1];
      for (int i = 2; i < splittedImput.length; i++) {
        result += " " + splittedImput[i];
      }
    } catch (ArrayIndexOutOfBoundsException ex) {
      return "unknown";
    }
    return result;
  }
}
