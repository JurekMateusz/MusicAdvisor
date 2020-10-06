package advisor.model.api.playlist;

import advisor.model.api.ExternalUrls;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.List;

@Getter
public class PlaylistRoot {
    private Playlist playlists;
}

@Getter
class Playlist {
    private List<Item> items;
}

@Getter
class Item {
    private String description;
    private String name;
    @SerializedName("external_urls")
    private ExternalUrls url;
}
