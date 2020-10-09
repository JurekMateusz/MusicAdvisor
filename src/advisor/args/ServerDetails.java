package advisor.args;

public class ServerDetails {
    private String serverAccessPath;
    private String serverApiPath;

    private ServerDetails() {
    }

    private ServerDetails(String serverAccessPath, String serverApiPath) {
        this.serverAccessPath = serverAccessPath;
        this.serverApiPath = serverApiPath;
    }

    public static ServerDetails of(String serverAccessPath, String serverApiPath) {
        return new ServerDetails(serverAccessPath, serverApiPath);
    }

    public String getServerAccessPath() {
        return serverAccessPath;
    }

    public String getServerApiPath() {
        return serverApiPath;
    }
}
