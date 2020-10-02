package advisor.music.task;

import advisor.music.Category;
import advisor.music.Option;

import static advisor.music.Option.UNKNOWN;

public interface MusicAdvisorTask {
    default Option parseOptionInput(String input) throws IllegalArgumentException {
        input = input.split(" ")[0].toUpperCase();
        try {
            return Option.valueOf(input);
        } catch (IllegalArgumentException ex) {
            return UNKNOWN;
        }
    }


    default Category parseCategoryInput(String input) {
        input = input.split(" ")[1].toUpperCase();// todo indexOutOfBounds for input "playlist"
        return Category.valueOf(input);
    }

    void execute();
}
