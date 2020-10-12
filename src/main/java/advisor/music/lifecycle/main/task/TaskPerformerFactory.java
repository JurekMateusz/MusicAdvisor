package advisor.music.lifecycle.main.task;

import advisor.music.lifecycle.Task;
import advisor.music.lifecycle.main.task.tasks.*;

import java.util.HashMap;
import java.util.Map;

public class TaskPerformerFactory {
  // single thread app ,dont need use immutable map.
  private static final Map<Task, InputTaskAbstract> map;

  static {
    map = new HashMap<>();
    map.put(Task.NEW, new NewsTask());
    map.put(Task.CATEGORIES, new CategoriesTask());
    map.put(Task.FEATURED, new FeaturedTask());
    map.put(Task.PLAYLISTS, new PlaylistTask());
    map.put(Task.NEXT, new NextTask());
    map.put(Task.PREV, new PrevTask());
    map.put(Task.UNKNOWN, new UnknownTask());
  }

  public static InputTaskAbstract get(Task task) {
    return map.getOrDefault(task, new UnknownTask());
  }
}
