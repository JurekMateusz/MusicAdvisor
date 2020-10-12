package advisor.model.api;

import com.google.gson.annotations.SerializedName;


public class ExternalUrls {
    @SerializedName("spotify")
    private String spotifyUrl;

    public String getSpotifyUrl() {
        return spotifyUrl;
    }

    @Override
    public String toString() {
        return spotifyUrl;
    }
}
