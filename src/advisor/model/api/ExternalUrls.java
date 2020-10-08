package advisor.model.api;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class ExternalUrls {
    @SerializedName("spotify")
    private String spotifyUrl;

    @Override
    public String toString() {
        return spotifyUrl;
    }
}
