package advisor.music.input;

public enum Task {
  NEWS("new"),
  AUTH("auth"),
  FEATURED("featured"),
  CATEGORIES("categories"),
  PLAYLIST("playlist"),
  PREVIOUS("prev"),
  NEXT("next"),
  EXIT("exit"),
  INFO("info"),
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

  public String getName() {
    return name;
  }
}
