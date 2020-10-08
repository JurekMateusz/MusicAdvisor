package advisor.model.api.playlist;

import advisor.model.api.ExternalUrls;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class Song {
    private String description;
    private String name;
    @SerializedName("external_urls")
    private ExternalUrls url;
    @Override
    public String toString(){
        return name + System.lineSeparator()
                + url.toString() + System.lineSeparator();
    }
}
