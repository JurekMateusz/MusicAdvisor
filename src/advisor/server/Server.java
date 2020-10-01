package advisor.server;

import advisor.MusicAdvisor;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.regex.Pattern;

public class Server {
    private static final String SUCCESS_MESSAGE = "Got the code. Return back to your program.";
    private static final String FAIL_MESSAGE = "Not found authorization code. Try again.";
    private final MusicAdvisor musicAdvisor;
    private HttpServer server;

    public Server(MusicAdvisor musicAdvisor) {
        this.musicAdvisor = musicAdvisor;
        try {
            this.server = HttpServer.create();
            configureServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(new MusicAdvisor());
        server.start();
    }

    public void start() {
        this.server.start();
    }

    public void stop() {
        this.server.stop(0);
    }

    private void configureServer() throws IOException {
        server.bind(new InetSocketAddress(8080), 0);
        server.createContext("/",
                exchange -> {
                    String response = SUCCESS_MESSAGE;
                    String query = exchange.getRequestURI().getQuery();
                    if (!isValid(query)) {
                        response = FAIL_MESSAGE;
                    }
                    exchange.sendResponseHeaders(200, response.length());
                    exchange.getResponseBody().write(response.getBytes());
                    exchange.getResponseBody().close();
                    forwardAuthCode(query);
                }
        );
    }

    private boolean isValid(String query) {
        return Objects.nonNull(query) && Pattern.compile("code=.*").matcher(query).find();
    }

    private void forwardAuthCode(String query) {
        if (Objects.nonNull(query)) {
            String code = getCodeFromQuery(query);
            musicAdvisor.setCode(code);
        }
        musicAdvisor.setUserTryAuthenticateYourself(true);
    }

    private String getCodeFromQuery(String query) {
        int index = query.indexOf("=");
        if (index == -1) {
            throw new IllegalArgumentException("Code not found for query: " + query);
        }
        return query.substring(index + 1);
    }
}
