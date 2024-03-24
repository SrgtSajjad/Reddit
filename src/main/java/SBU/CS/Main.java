package SBU.CS;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {

    }

    public static void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    public static int handleErrors(int firstValue, int lastValue) { // for handling exceptions in getting input
        Scanner scanner = new Scanner(System.in);

        int number = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.printf("Enter an option (from %d to %d)", firstValue, lastValue);
            String input = scanner.nextLine();

            try {
                number = Integer.parseInt(input);
                // Check if the input is a valid option
                if (number >= firstValue && number <= lastValue) {
                    validInput = true;
                } else {
                    System.out.printf("Enter an option (from %d to %d)\n", firstValue, lastValue);
                }
            } catch (NumberFormatException e) {
                // Handle the case where input is not a valid integer
                System.out.println("Invalid input: Please enter a valid integer.");
            }
        }
        // Close the scanner
        scanner.close();

        return number;
    }
}
