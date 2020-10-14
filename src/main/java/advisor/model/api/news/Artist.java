package advisor.model.api.news;

import lombok.Getter;

@Getter
public class Artist {
  private String name;

  @Override
  public String toString() {
    return name;
  }
}
