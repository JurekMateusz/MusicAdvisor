package advisor.view;

import advisor.model.view.Result;

public class Console {
  public static void log(String text) {
    System.out.println(text);
  }

  public static void log(Result result) {
    String message =
        "---------------------------"
            + System.lineSeparator()
            + result.getOutput()
            + System.lineSeparator()
            + "---------------------------";
    System.out.println(message);
  }
}
