package advisor.args;

import lombok.*;

@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ServerDetails {
  private final String serverAccessPath;
  private final String serverApiPath;
  private final int numberOfEntriesInPage;

  public static ServerDetails of(
      String serverAccessPath, String serverApiPath, int numberOfEntriesInPage) {
    return new ServerDetails(serverAccessPath, serverApiPath, numberOfEntriesInPage);
  }
}
