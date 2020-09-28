package advisor;


import java.util.Scanner;

import static advisor.Option.*;

public class MusicAdvisor {
    private boolean isRunning = true;
    private boolean isAuthenticated = false;

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
        while (!isAuthenticated) {
            String input = scanner.nextLine();
            Option option = parseOptionInput(input);
            if (option == EXIT) {
                printGoodbye();
                System.exit(0);
            }
            if (option != AUTH) {
                printInfoAuthForUser();
                continue;
            }
            printAuthLink();
            isAuthenticated = true;
        }
        printSuccessAuthentication();
    }

    private Option parseOptionInput(String input) throws IllegalArgumentException {
        input = input.split(" ")[0].toUpperCase();
        try {
            return Option.valueOf(input);
        } catch (IllegalArgumentException ex) {
            return UNKNOWN;
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
        System.out.println("https://accounts.spotify.com/authorize?" +
                "client_id=2ee3d9aa7be04620bbc2838939e84407" +
                "&redirect_uri=http://localhost:8080&response_type=code");
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

    private void printIllegalArgumentMessage(String input) {
        System.out.println("Dont recognize this input : " + input);
    }
}
