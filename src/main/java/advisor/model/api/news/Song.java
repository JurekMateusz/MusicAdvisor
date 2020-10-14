package advisor.model.api.news;

import advisor.model.api.ExternalUrls;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.List;

@Getter
public class Song {
  private List<Artist> artists;

  @SerializedName("external_urls")
  private ExternalUrls externalUrls;

  private String name;
}
