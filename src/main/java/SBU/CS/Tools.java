package SBU.CS;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {

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
            System.out.printf("Enter %s (from %d to %d)", inputTitle, firstValue, lastValue);
            String input = scanner.nextLine();

            try {
                number = Integer.parseInt(input);
                // Check if the input is a valid option
                if (number >= firstValue && number <= lastValue) {
                    validInput = true;
                } else {
                    System.out.printf("Enter %s (from %d to %d)\n", inputTitle, firstValue, lastValue);
                }
            } catch (NumberFormatException e) {
                // Handle the case where input is not a valid number
                System.out.println("Invalid input: Please enter a valid number.");
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
}
