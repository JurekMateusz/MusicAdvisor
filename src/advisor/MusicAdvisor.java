package advisor;

import java.util.Scanner;

public class MusicAdvisor {
    private boolean isRunning = true;

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (isRunning) {
            String input = scanner.nextLine();
            switch (input) {
                case "new":
                    printNewReleases();
                    break;
                case "featured":
                    printFeatured();
                    break;
                case "categories":
                    printCategories();
                    break;
                case "playlists Mood":
                    printPlayListMood();
                    break;
                case "exit":
                    turnOffApp();
                    printGoodbye();
                    break;
                default:
                    throw new IllegalArgumentException(input + " dont know");

            }
        }
        scanner.close();
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

}
