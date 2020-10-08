package advisor.model.api.playlist;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.List;

@Getter
public class Playlist {
    @SerializedName("items")
    private List<Song> songs;
}
