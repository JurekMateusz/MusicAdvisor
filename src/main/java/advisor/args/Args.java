package advisor.args;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Args {
  static final String DEFAULT_AUTHORIZATION_SERVER_PATH = "https://accounts.spotify.com";
  static final String DEFAULT_API_SERVER_PATH = "https://api.spotify.com";
  static final int DEFAULT_NUMBER_OF_ENTRIES_ON_PAGE = 8;
  static final String ARG_NAME_AUTHORIZATION_SERVER = "-access";
  static final String ARG_NAME_API_SEVER = "-resource";
  static final String ARG_NAME_PAGE = "-page";
  static final String ARG_CLIENT_ID = "-client_id";
  static final String ARG_CLIENT_SECRET = "-client_secret";
  static final String MSG_FOR_SAME_ID = "Client IDs not parsed";
  static final String MSG_FOR_MISSING = "Missing key or argument";
  static final String MSG_FOR_REPEAT = "Arguments repeat";
  private static final String DEFAULT_CLIENT_ID = "---";
  private final Map<String, String> ARGS;
  private final SpotifyDashboardIDs spotifyDashboardIDs;
  private final ServerDetails serverDetails;

  {
    ARGS = new HashMap<>();
    ARGS.put(ARG_NAME_AUTHORIZATION_SERVER, DEFAULT_AUTHORIZATION_SERVER_PATH);
    ARGS.put(ARG_NAME_API_SEVER, DEFAULT_API_SERVER_PATH);
    ARGS.put(ARG_NAME_PAGE, String.valueOf(DEFAULT_NUMBER_OF_ENTRIES_ON_PAGE));
    ARGS.put(ARG_CLIENT_ID, DEFAULT_CLIENT_ID);
    ARGS.put(ARG_CLIENT_SECRET, DEFAULT_CLIENT_ID);
  }

  public Args(String... args) throws IllegalArgumentException {
    validateArgs(args);
    ARGS.forEach(
        (key, defaults) -> {
          ARGS.put(key, findArgument(key, defaults, args));
        });
    spotifyDashboardIDs =
        SpotifyDashboardIDs.of(ARGS.get(ARG_CLIENT_ID), ARGS.get(ARG_CLIENT_SECRET));
    serverDetails =
        ServerDetails.builder()
            .serverAccessPath(ARGS.get(ARG_NAME_AUTHORIZATION_SERVER))
            .serverApiPath(ARGS.get(ARG_NAME_API_SEVER))
            .numberOfEntriesInPage(Integer.parseInt(ARGS.get(ARG_NAME_PAGE)))
            .build();
  }

  private void validateArgs(String[] args) {
    if (isOdd(args)) {
      throw new IllegalArgumentException(MSG_FOR_MISSING);
    }
    int lengthDistinctElements = getLengthDistinctElements(args);
    if (lengthDistinctElements != args.length) {
      throw new IllegalArgumentException(MSG_FOR_REPEAT);
    }
  }

  private boolean isOdd(String[] args) {
    return args.length % 2 == 1;
  }

  private int getLengthDistinctElements(String[] args) {
    return Arrays.stream(args).distinct().toArray().length;
  }

  private String findArgument(String argument, String defaultt, String[] args) {
    for (int i = 0; i < args.length - 1; i++) {
      if (args[i].equalsIgnoreCase(argument) && i + 1 < args.length) {
        return args[i + 1];
      }
    }
    return defaultt;
  }

  public SpotifyDashboardIDs getSpotifyDashboardIDs() throws IllegalStateException {
    validateSpotify();
    return spotifyDashboardIDs;
  }

  private void validateSpotify() {
    String clientId = spotifyDashboardIDs.getClientID();
    String clientSecret = spotifyDashboardIDs.getClientSecret();
    if (clientId.equals(DEFAULT_CLIENT_ID) || clientSecret.equals(DEFAULT_CLIENT_ID)) {
      throw new IllegalStateException(MSG_FOR_SAME_ID);
    }
  }

  public ServerDetails getServerDetails() {
    return serverDetails;
  }
}
