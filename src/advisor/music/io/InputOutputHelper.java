package advisor.music.io;

import advisor.model.token.AccessToken;
import com.google.gson.Gson;

import java.util.Scanner;

public class InputOutputHelper {
    private Scanner scanner = new Scanner(System.in);

    public String readInput() {
        return scanner.nextLine().trim();
    }

    public void printInfoAuthForUser() {
        System.out.println("Please, provide access for application.");
    }


    public void printSuccessAuthentication() {
        System.out.println("---SUCCESS---");
    }


    public void printNewReleases() {
        System.out.println("---NEW RELEASES---\n" +
                "Mountains [Sia, Diplo, Labrinth]\n" +
                "Runaway [Lil Peep]\n" +
                "The Greatest Show [Panic! At The Disco]\n" +
                "All Out Life [Slipknot]");
    }

    public void printFeatured() {
        System.out.println("---FEATURED---\n" +
                "Mellow Morning\n" +
                "Wake Up and Smell the Coffee\n" +
                "Monday Motivation\n" +
                "Songs to Sing in the Shower");
    }

    public void printCategories() {
        System.out.println("---CATEGORIES---\n" +
                "Top Lists\n" +
                "Pop\n" +
                "Mood\n" +
                "Latin");
    }

    public void printPlayListMood() {
        System.out.println("---MOOD PLAYLISTS---\n" +
                "Walk Like A Badass  \n" +
                "Rage Beats  \n" +
                "Arab Mood Booster  \n" +
                "Sunday Stroll");
    }


    public void printGoodbye() {
        System.out.println("---GOODBYE!---");
    }

    public void printWaitingForCode() {
        System.out.println("waiting for code...");
    }

    public void printCodeReceived() {
        System.out.println("code received");
    }

    public void printIllegalArgumentMessage(String input) {
        System.out.println("Dont recognize this input : " + input);
    }

    public void printAccessTokenAsJson(AccessToken accessToken) {
        System.out.println("response:" + System.lineSeparator() + new Gson().toJson(accessToken));
    }

    public void print(String text) {
        System.out.println(text);
    }

    public void printProcedureAccessToken() {
        System.out.println("making http request for access_token...");
    }

}
