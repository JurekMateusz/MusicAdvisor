package advisor.music;

public enum Option {
    NEW("new"),
    AUTH("auth"),
    FEATURED("featured"),
    CATEGORIES("categories"),
    PLAYLIST("playlists"),
    EXIT("exit"),
    UNKNOWN("unknown");
    private String name;

    Option(String name) {
        this.name = name;
    }
}
