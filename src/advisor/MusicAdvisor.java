package advisor;


import advisor.httpclient.Request;
import advisor.model.token.AccessToken;
import advisor.server.Server;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Scanner;

import static advisor.Option.*;

public class MusicAdvisor {
    private static final String AUTHORIZE_USER_LINK = "https://accounts.spotify.com/authorize?" +
            "client_id=2ee3d9aa7be04620bbc2838939e84407" +
            "&redirect_uri=http://localhost:8080&response_type=code";
    private AccessToken accessToken;
    private String code = "";
    private boolean isRunning = true;
    private boolean isAuthenticated = false;
    private boolean isUserTryAuthenticateYourself = false;

    private Request request = new Request();

    public void start() {
        Scanner scanner = new Scanner(System.in);
        Option option;
        Category category;

        authenticateUser(scanner);

        while (isRunning) {
            String input = scanner.nextLine().trim();
            option = parseOptionInput(input);

            switch (option) {
                case NEW:
                    printNewReleases();
                    break;
                case FEATURED:
                    printFeatured();
                    break;
                case CATEGORIES:
                    printCategories();
                    break;
                case PLAYLIST:
                    category = parseCategoryInput(input);//todo
                    printPlayListMood();
                    break;
                case EXIT:
                    turnOffApp();
                    printGoodbye();
                case UNKNOWN:
                    printIllegalArgumentMessage(input);
            }
        }
        scanner.close();
    }

    private void authenticateUser(Scanner scanner) {
        Server server = new Server(this);
        server.start();

        while (!isAuthenticated) {
            String input = scanner.nextLine();
            Option option = parseOptionInput(input);
            if (option == EXIT) {
                printGoodbye();
                server.stop();
                System.exit(0);
            }
            if (option != AUTH) {
                printInfoAuthForUser();
                continue;
            }
            printAuthLink();
            printWaitingForCode();
            this.stopThisExecutionUntilUserAuthenticate();
            printCodeReceived();
            if (code.length() == 0) {
                isUserTryAuthenticateYourself = false;
                continue;
            }
            try {
                this.accessToken = getAccessToken();
                printAccessTokenAsJson();
                isAuthenticated = true;
            } catch (IOException | InterruptedException ex) {
                System.out.println("Fail to get access token form code");
                isUserTryAuthenticateYourself = false;
            }
        }
        printSuccessAuthentication();
        server.stop();
    }

    private AccessToken getAccessToken() throws IOException, InterruptedException {
        printProcedureAccessToken();
        return request.getAccessToken(code);
    }

    private void printProcedureAccessToken() {
        System.out.println("making http request for access_token...");
    }


    private Option parseOptionInput(String input) throws IllegalArgumentException {
        input = input.split(" ")[0].toUpperCase();
        try {
            return Option.valueOf(input);
        } catch (IllegalArgumentException ex) {
            return UNKNOWN;
        }
    }

    private synchronized void stopThisExecutionUntilUserAuthenticate() {
        while (!isUserTryAuthenticateYourself) {
            try {
                this.wait(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Category parseCategoryInput(String input) {
        input = input.split(" ")[1].toUpperCase();
        return Category.valueOf(input);
    }

    private void printInfoAuthForUser() {
        System.out.println("Please, provide access for application.");
    }

    private void printAuthLink() {
        System.out.println(AUTHORIZE_USER_LINK);
    }

    private void printSuccessAuthentication() {
        System.out.println("---SUCCESS---");
    }


    private void printNewReleases() {
        System.out.println("---NEW RELEASES---\n" +
                "Mountains [Sia, Diplo, Labrinth]\n" +
                "Runaway [Lil Peep]\n" +
                "The Greatest Show [Panic! At The Disco]\n" +
                "All Out Life [Slipknot]");
    }

    private void printFeatured() {
        System.out.println("---FEATURED---\n" +
                "Mellow Morning\n" +
                "Wake Up and Smell the Coffee\n" +
                "Monday Motivation\n" +
                "Songs to Sing in the Shower");
    }

    private void printCategories() {
        System.out.println("---CATEGORIES---\n" +
                "Top Lists\n" +
                "Pop\n" +
                "Mood\n" +
                "Latin");
    }

    private void printPlayListMood() {
        System.out.println("---MOOD PLAYLISTS---\n" +
                "Walk Like A Badass  \n" +
                "Rage Beats  \n" +
                "Arab Mood Booster  \n" +
                "Sunday Stroll");
    }

    private void turnOffApp() {
        this.isRunning = false;
    }

    private void printGoodbye() {
        System.out.println("---GOODBYE!---");
    }

    private void printWaitingForCode() {
        System.out.println("waiting for code...");
    }

    private void printCodeReceived() {
        System.out.println("code received");
    }

    private void printIllegalArgumentMessage(String input) {
        System.out.println("Dont recognize this input : " + input);
    }

    public void setCode(String code) {
        this.code = code;
    }

    private void printAccessTokenAsJson() throws JsonProcessingException {
        System.out.println("response:" + System.lineSeparator() + new ObjectMapper().writeValueAsString(accessToken));
    }

    public void setUserTryAuthenticateYourself(boolean status) {
        this.isUserTryAuthenticateYourself = status;
    }
}
