package advisor.model.api.categories;

import lombok.Getter;

@Getter
public class Category {
  private String id;
  private String name;

  @Override
  public String toString() {
    return name;
  }
}
