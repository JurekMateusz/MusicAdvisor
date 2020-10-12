package advisor.args;

public class ServerDetails {
    private String serverAccessPath;
    private String serverApiPath;
    private int numberOfEntriesInPage;

    private ServerDetails() {
    }

    private ServerDetails(String serverAccessPath, String serverApiPath, int numberOfEntriesInPage) {
        this.serverAccessPath = serverAccessPath;
        this.serverApiPath = serverApiPath;
        this.numberOfEntriesInPage = numberOfEntriesInPage;
    }

    public static ServerDetails of(String serverAccessPath, String serverApiPath, int numberOfEntriesInPage) {
        return new ServerDetails(serverAccessPath, serverApiPath, numberOfEntriesInPage);
    }

    public String getServerAccessPath() {
        return serverAccessPath;
    }

    public String getServerApiPath() {
        return serverApiPath;
    }

    public int getNumberOfEntriesInPage() {
        return numberOfEntriesInPage;
    }
}
