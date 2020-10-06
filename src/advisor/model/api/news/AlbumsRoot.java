package advisor.model.api.news;

import advisor.model.api.ExternalUrls;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.List;

@Getter
public class AlbumsRoot {
    private Albums albums;
}

@Getter
class Albums {
    @SerializedName("href")
    private String href;
    private List<Item> items;
}

@Getter
class Artist {
    private String name;
}

@Getter
class Item {
    private List<Artist> artists;
    @SerializedName("external_urls")
    private ExternalUrls externalUrls;
    private String name;
}
