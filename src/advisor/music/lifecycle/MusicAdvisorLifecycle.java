package advisor.music.lifecycle;

import java.util.Scanner;

import static advisor.music.lifecycle.Task.UNKNOWN;

public interface MusicAdvisorLifecycle {
    Scanner scanner = new Scanner(System.in);

    default UserInput getUserInput() {
        String rawInput = scanner.nextLine();
        rawInput = rawInput.trim().toUpperCase();
        String[] split = rawInput.split(" +");
        if (split.length == 0) {
            return UserInput.of(UNKNOWN, "unknown");
        }
        Task task = parseTaskInput(split[0]);
        String category = getCategory(split);

        return UserInput.of(task, category);
    }

    private Task parseTaskInput(String input) {
        try {
            return Task.valueOf(input);
        } catch (IllegalArgumentException ex) {
            return UNKNOWN;
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

    void execute();
}
