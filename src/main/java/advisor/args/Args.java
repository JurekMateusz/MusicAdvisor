package advisor.args;

import java.util.Arrays;

public class Args {
  private static final String[] ARGS;

  private static final String DEFAULT_AUTHORIZATION_SERVER_PATH = "https://accounts.spotify.com";
  private static final String DEFAULT_API_SERVER_PATH = "https://api.spotify.com";
  private static final int DEFAULT_NUMBER_OF_ENTRIES_ON_PAGE = 5;
  private static final String ARG_NAME_AUTHORIZATION_SERVER = "-access";
  private static final String ARG_NAME_API_SEVER = "-resource";
  private static final String ARG_NAME_PAGE = "-page";
  private static final String ARG_CLIENT_ID = "-client_id";
  private static final String ARG_CLIENT_SECRET = "-client_secret";

  static {
    ARGS =
        new String[] {
          ARG_NAME_AUTHORIZATION_SERVER,
          ARG_NAME_API_SEVER,
          ARG_NAME_PAGE,
          ARG_CLIENT_ID,
          ARG_CLIENT_SECRET
        };
  }

  private ServerDetails serverDetails;
  private final SpotifyDashboardIDs spotifyDashboardIDs;

  public Args(String... args) {
    String clientId = findArgument(ARG_CLIENT_ID, args);
    String clientSecret = findArgument(ARG_CLIENT_SECRET, args);
    spotifyDashboardIDs = SpotifyDashboardIDs.of(clientId, clientSecret);
    if (isNoArgsServer(args)) {
      serverDetails =
          ServerDetails.of(
              DEFAULT_AUTHORIZATION_SERVER_PATH,
              DEFAULT_API_SERVER_PATH,
              DEFAULT_NUMBER_OF_ENTRIES_ON_PAGE);
      return;
    }
    if (isPassedOnlyServerDetails(args)) {
      String authorizationServer = findArgument(ARG_NAME_AUTHORIZATION_SERVER, args);
      String apiServer = findArgument(ARG_NAME_API_SEVER, args);
      serverDetails =
          ServerDetails.of(authorizationServer, apiServer, DEFAULT_NUMBER_OF_ENTRIES_ON_PAGE);
    }
    if (isPassedOnlyNumberOfEntriesOnPage(args)) {
      String numberInPage = findArgument(ARG_NAME_PAGE, args);
      int numberInPageParsed = parse(numberInPage);
      serverDetails =
          ServerDetails.of(
              DEFAULT_AUTHORIZATION_SERVER_PATH, DEFAULT_API_SERVER_PATH, numberInPageParsed);
    }
    convertArgs(args);
  }

  private boolean isNoArgsServer(String[] args) {
    return args.length == 4;
  }

  private boolean isPassedOnlyServerDetails(String[] args) {
    if (args.length == 4) return true;
    int serverArg = countAppearances(args, ARG_NAME_AUTHORIZATION_SERVER);
    int apiArg = countAppearances(args, ARG_NAME_API_SEVER);
    return isParameterAppearancesOnce(serverArg, apiArg);
  }

  private boolean isParameterAppearancesOnce(int... args) {
    return Arrays.stream(args).allMatch(value -> value == 1);
  }

  private int countAppearances(String[] args, String argument) {
    return (int) Arrays.stream(args).filter(s -> s.equalsIgnoreCase(argument)).count();
  }

  private String findArgument(String argument, String[] args) {
    for (int i = 0; i < args.length - 1; i++) {
      if (args[i].equalsIgnoreCase(argument)) {
        return args[i + 1];
      }
    }
    throw new IllegalArgumentException(
        "Not found arg: " + argument + System.lineSeparator() + "Args passed:" + args.toString());
  }

  private boolean isPassedOnlyNumberOfEntriesOnPage(String[] args) {
    if (args.length == 2) return true;
    int arg = countAppearances(args, ARG_NAME_PAGE);
    return isParameterAppearancesOnce(arg);
  }

  private int parse(String numberInPage) {
    try {
      return Integer.parseInt(numberInPage);
    } catch (NumberFormatException ex) {
      throw new IllegalArgumentException("Can't parse: " + numberInPage);
    }
  }

  private void convertArgs(String[] args) {
    validateArgs(args);
    String authorizationServer = findArgument(ARG_NAME_AUTHORIZATION_SERVER, args);
    String apiServer = findArgument(ARG_NAME_API_SEVER, args);
    String numberInPage = findArgument(ARG_NAME_PAGE, args);
    int numberInPageParsed = parse(numberInPage);
    serverDetails = ServerDetails.of(authorizationServer, apiServer, numberInPageParsed);
  }

  private void validateArgs(String[] args) {
    int firstArg = countAppearances(args, ARG_NAME_AUTHORIZATION_SERVER);
    int secondArg = countAppearances(args, ARG_NAME_API_SEVER);
    int thirdArg = countAppearances(args, ARG_NAME_PAGE);
    if (!isParameterAppearancesOnce(firstArg, secondArg, thirdArg)) {
      throw new IllegalArgumentException("Program arguments incorrect. Args:" + args.toString());
    }
  }

  public ServerDetails getServerDetails() {
    return serverDetails;
  }

  public SpotifyDashboardIDs getSpotifyDashboardIDs() {
    return spotifyDashboardIDs;
  }
}
