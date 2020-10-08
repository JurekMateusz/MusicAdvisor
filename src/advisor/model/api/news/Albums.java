package advisor.model.api.news;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.List;

@Getter
public class Albums {
    private String href;
    @SerializedName("items")
    private List<Song> songs;
}
