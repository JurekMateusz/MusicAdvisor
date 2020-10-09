package advisor.model.api.news;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Albums {
    private String href;
    @SerializedName("items")
    private List<Song> songs;

    public String getHref() {
        return href;
    }

    public List<Song> getSongs() {
        return songs;
    }
}
