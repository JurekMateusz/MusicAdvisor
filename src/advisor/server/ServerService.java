package advisor.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.regex.Pattern;

public class ServerService {
    private static final Integer MIN_CODE_LENGTH_VALID = 5; //160
    private Semaphore semaphore;
    private Server server;

    public String getUserSpotifyCode() {
        semaphore = new Semaphore(1);
        server = new Server();
        server.start();
        String code = getCodeWithDelay();
        server.stop();
        return code;
    }

    private String getCodeWithDelay() {
        stopThisExecutionUntilUserAuthenticateYourselfInSpotify();
        return server.code;
    }

    private void stopThisExecutionUntilUserAuthenticateYourselfInSpotify() {
        try {
            semaphore.acquire(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class Server {
        static final String SUCCESS_MESSAGE = "Got the code. Return back to your program.";
        static final String FAIL_MESSAGE = "Authorization code not found. Try again.";
        HttpServer server;
        String code;

        Server() {
            try {
                this.server = HttpServer.create();
                configureServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!semaphore.tryAcquire(1)) {
                throw new IllegalStateException("Semaphore not taken");
            }
        }

        void configureServer() throws IOException {
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

        void forwardAuthCode(String query) {
            if (!isValid(query)) {
                return;
            }
            code = getCodeFromQuery(query);
            semaphore.release(1);
        }

        void start() {
            this.server.start();
        }

        void stop() {
            this.server.stop(1);
        }
    }

    private static boolean isValid(String query) {
        return Objects.nonNull(query)
                && Pattern.compile("code=.*").matcher(query).find()
                && getCodeFromQuery(query).length() >= MIN_CODE_LENGTH_VALID;
    }

    private static String getCodeFromQuery(String query) {
        int index = query.indexOf("=");
        if (index == -1) {
            throw new IllegalArgumentException("Code not found for query: " + query);
        }
        return query.substring(index + 1);
    }
}
