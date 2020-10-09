package advisor.music.lifecycle.main.task;

import advisor.exception.ContentNotFoundException;
import advisor.http.service.RequestService;
import advisor.model.api.categories.Category;
import advisor.music.lifecycle.UserInput;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public abstract class InputTaskAbstract {
    protected static final RequestService service = RequestService.getInstance();
    protected static final String newLine = System.lineSeparator();
    protected static List<Category> categories = Collections.emptyList();

    public abstract void perform(String accessToken, UserInput input) throws IOException, InterruptedException,
            ContentNotFoundException, InterruptedException;
}
