package advisor.music.lifecycle.input;

public enum Task {
  NEW("new"),
  AUTH("auth"),
  FEATURED("featured"),
  CATEGORIES("categories"),
  PLAYLISTS("playlists"),
  PREV("prev"),
  NEXT("next"),
  EXIT("exit"),
  UNKNOWN("unknown");
  private String name;

  Task() {}

  Task(String name) {
    this.name = name;
  }

  public Task set(String input) {
    this.name = input;
    return this;
  }
}
