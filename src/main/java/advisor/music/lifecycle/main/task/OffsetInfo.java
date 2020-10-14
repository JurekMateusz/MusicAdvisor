package advisor.music.lifecycle.main.task;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OffsetInfo {
  private String next;
  private int offset;
  private String previous;
  private int total;
  private int limit;
}
