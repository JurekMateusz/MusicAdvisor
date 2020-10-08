package advisor.model.api.categories;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.List;

@Getter
public class Categories {
    @SerializedName("items")
    private List<Category> categories;
}

