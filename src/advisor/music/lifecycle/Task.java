package advisor.music.lifecycle;

public enum Task {
    NEW("new"),
    AUTH("auth"),
    FEATURED("featured"),
    CATEGORIES("categories"),
    PLAYLIST("playlists"),
    EXIT("exit"),
    UNKNOWN("unknown");
    private String name;

    Task(String name) {
        this.name = name;
    }
}