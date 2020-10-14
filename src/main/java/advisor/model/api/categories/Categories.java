package advisor.model.api.categories;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class Categories {
  @SerializedName("items")
  private List<Category> categories;

  private String next;
  private int offset;
  private String previous;
  private int total;
  private int limit;
}
