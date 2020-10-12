package advisor.model.api.news;

import advisor.model.api.ExternalUrls;
import com.google.gson.annotations.SerializedName;

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
}
