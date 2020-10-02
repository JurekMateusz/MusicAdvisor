package advisor.music;

public enum Category {
    TOP_LISTS("Top Lists"),
    POP("Pop"),
    MOOD("Mood"),
    LATIN("Latin");
    private String name;

    Category(String name) {
        this.name = name;
    }
}
