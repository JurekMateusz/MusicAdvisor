package advisor.music.lifecycle;

public final class UserInput {
    private Task task;
    private String category;

    private UserInput() {
    }

    private UserInput(Task task, String category) {
        this.task = task;
        this.category = category;
    }

    public static UserInput of(Task task, String category) {
        return new UserInput(task, category);
    }

    public Task getTask() {
        return task;
    }

    public String getCategory() {
        return category;
    }
}
