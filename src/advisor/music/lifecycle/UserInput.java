package advisor.music.lifecycle;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public final class UserInput {
    private Task task;
    private String category;

    public static UserInput of(Task task, String category) {
        return new UserInput(task, category);
    }
}
