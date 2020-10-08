package advisor.model.api.news;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Artist {
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
