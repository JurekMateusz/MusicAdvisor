package advisor.model.api.categories;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Categories {
    @SerializedName("items")
    private List<Category> categories;
    private String next;
    private int offset;
    private String previous;
    private int total;
    private int limit;

    public int getLimit() {
        return limit;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public String getNext() {
        return next;
    }

    public int getOffset() {
        return offset;
    }

    public String getPrevious() {
        return previous;
    }

    public int getTotal() {
        return total;
    }
}

