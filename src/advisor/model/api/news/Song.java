package advisor.model.api.news;

import advisor.model.api.ExternalUrls;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;


public class Song {
    private List<Artist> artists;
    @SerializedName("external_urls")
    private ExternalUrls externalUrls;
    private String name;

    public List<Artist> getArtists() {
        return artists;
    }

    public ExternalUrls getExternalUrls() {
        return externalUrls;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + System.lineSeparator()
                + Arrays.toString(artists.toArray())
                + System.lineSeparator() + externalUrls.toString() + System.lineSeparator();
    }
}

