package SBU.CS;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {

    public static final String RESET_COLOR = "\u001B[0m";
    public static final String BLACK_COLOR = "\u001B[30m";
    public static final String RED_COLOR = "\u001B[31m";
    public static final String GREEN_COLOR = "\u001B[32m";
    public static final String YELLOW_COLOR = "\u001B[33m";
    public static final String BLUE_COLOR = "\u001B[34m";
    public static final String PURPLE_COLOR = "\u001B[35m";
    public static final String CYAN_COLOR = "\u001B[36m";
    public static final String WHITE_COLOR = "\u001B[37m";

    public static boolean stringIsValid(String enteredString) {

        // Regular expression to match at least 8 characters, containing only alphabets, numbers, and underscores
        String pattern = "^[a-zA-Z0-9_]{8,}$";
        System.out.print("Invalid input: Entered string should be at least 8 characters and only contain alphabets, numbers and underscores");

        return Pattern.matches(pattern, enteredString);


    }

    public static void clearScreen() { // create the illusion of a cleared terminal by printing empty lines
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    public static int handleErrors(String inputTitle, int firstValue, int lastValue) { // for handling exceptions in getting input
        Scanner scanner = new Scanner(System.in);

        int number = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.printf("Enter %s (from %d to %d): ", inputTitle, firstValue, lastValue);
            String input = scanner.nextLine();

            try {
                number = Integer.parseInt(input);
                // Check if the input is a valid option
                if (number >= firstValue && number <= lastValue) {
                    validInput = true;
                } else {
                    System.out.println("Invalid input: Please enter a valid number");
                }
            } catch (NumberFormatException e) {
                // Handle the case where input is not a valid number
                System.out.println("Invalid input: Please enter a valid integer.");
            }
        }

        return number;
    }

    public static boolean validateEmailFormat(String email) { // validates an entered email's  format using regex
        String regex = ".+@.+\\..+";

        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        return matcher.find();
    }

    public static String calculateTimePassed(LocalDateTime timePublished) {
        LocalDateTime currentTime = LocalDateTime.now();
        String timePassed;

        if (currentTime.getYear() - timePublished.getYear() > 0) {
            timePassed = currentTime.getYear() - timePublished.getYear() + " y";
        } else if (currentTime.getMonthValue() - timePublished.getMonthValue() > 0) {
            timePassed = currentTime.getMonthValue() - timePublished.getMonthValue() + " mo";
        } else if (currentTime.getDayOfMonth() - timePublished.getDayOfMonth() > 0) {
            timePassed = currentTime.getDayOfMonth() - timePublished.getDayOfMonth() + " d";
        } else if (currentTime.getHour() - timePublished.getHour() > 0) {
            timePassed = currentTime.getHour() - timePublished.getHour() + " h";
        } else if (currentTime.getMinute() - timePublished.getMinute() > 0) {
            timePassed = currentTime.getMinute() - timePublished.getMinute() + " min";
        } else {
            timePassed = "A few moments";
        }

        return timePassed;
    }
}
