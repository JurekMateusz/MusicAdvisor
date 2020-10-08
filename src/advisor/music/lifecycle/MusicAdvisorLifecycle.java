package advisor.music.lifecycle;

import java.util.Scanner;

import static advisor.music.lifecycle.Task.UNKNOWN;

public interface MusicAdvisorLifecycle {
    Scanner scanner = new Scanner(System.in);

    private String getCategory(String[] splittedImput) {
        try {
            return splittedImput[1];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return "unknown";
        }
    }

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

    void execute();
}
