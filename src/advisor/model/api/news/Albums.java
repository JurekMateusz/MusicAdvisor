package advisor.model.api.news;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Albums {
    private String href;
    @SerializedName("items")
    private List<Song> songs;
    private String next;
    private int offset;
    private String previous;
    private int total;
    private int limit;

    public int getLimit() {
        return limit;
    }

    public int getTotal() {
        return total;
    }

    public String getHref() {
        return href;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public String getNext() {
        return next;
    }

    public int getOffset() {
        return offset;
    }

    public String getPrevious() {
        return previous;
    }
}
