package advisor.model.api.playlist;

import advisor.model.api.ExternalUrls;
import com.google.gson.annotations.SerializedName;

public class Song {
  private String description;
  private String name;

  @SerializedName("external_urls")
  private ExternalUrls externalUrls;

  public String getDescription() {
    return description;
  }

  public String getName() {
    return name;
  }

  public ExternalUrls getExternalUrls() {
    return externalUrls;
  }
}
