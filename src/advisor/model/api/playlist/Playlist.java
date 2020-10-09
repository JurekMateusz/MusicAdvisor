package advisor.model.api.playlist;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Playlist {
    @SerializedName("items")
    private List<Song> songs;

    public List<Song> getSongs() {
        return songs;
    }
}
