package advisor.model.api.playlist;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Playlist {
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
