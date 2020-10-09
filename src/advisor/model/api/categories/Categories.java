package advisor.model.api.categories;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Categories {
    @SerializedName("items")
    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }
}

