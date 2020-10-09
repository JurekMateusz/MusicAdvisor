package advisor.args;

import java.util.Arrays;

public class Args {
    private static final String DEFAULT_AUTHORIZATION_SERVER_PATH = "https://accounts.spotify.com";
    private static final String DEFAULT_API_SERVER_PATH = "https://api.spotify.com";
    private static final String ARG_NAME_AUTHORIZATION_SERVER = "-access";
    private static final String ARG_NAME_API_SEVER = "-resource";
    private static final int NUMBER_OF_ARGS = 4;
    private ServerDetails serverDetails;

    public Args(String... args) {
        if (isNumberOfArgsCorrect(args)) {
            serverDetails = ServerDetails.of(DEFAULT_AUTHORIZATION_SERVER_PATH, DEFAULT_API_SERVER_PATH);
            return;
        }
        convertArgs(args);
    }

    private boolean isNumberOfArgsCorrect(String[] args) {
        return args.length != NUMBER_OF_ARGS;
    }

    private void convertArgs(String[] args) {
        validateArgs(args);
        String authorizationServer = findArgument(ARG_NAME_AUTHORIZATION_SERVER, args);
        String apiServer = findArgument(ARG_NAME_API_SEVER, args);
        serverDetails = ServerDetails.of(authorizationServer, apiServer);
    }

    private void validateArgs(String[] args) {
        int firstArg = countAppearances(args, ARG_NAME_AUTHORIZATION_SERVER);
        int secondArg = countAppearances(args, ARG_NAME_API_SEVER);
        if (!isParameterAppearancesOnce(firstArg, secondArg)) {
            throw new IllegalArgumentException("Program arguments incorrect. Args:" + args.toString());
        }
    }

    private boolean isParameterAppearancesOnce(int firstArg, int secondArg) {
        return firstArg == 1 && secondArg == 1;
    }

    private int countAppearances(String[] args, String argument) {
        return (int) Arrays.stream(args)
                .filter(s -> s.equalsIgnoreCase(argument)).count();
    }

    private String findArgument(String argNameApiSever, String[] args) {
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equalsIgnoreCase(argNameApiSever)) {
                return args[i + 1];
            }
        }
        throw new IllegalArgumentException("Not found arg: " + argNameApiSever + System.lineSeparator()
                + "Args passed:" + args.toString());
    }

    public ServerDetails getServerDetails() {
        return serverDetails;
    }
}
