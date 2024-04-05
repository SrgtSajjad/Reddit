package SBU.CS;

import SBU.CS.Account.Birthday;
import SBU.CS.Account.User;

import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        while (true) {
            Tools.clearScreen();
            System.out.println("~~| Reddit |~~");
            System.out.println("0. Exit\n1. Login\n2. Sign Up");
            Scanner scanner = new Scanner(System.in);
            int command = Tools.handleErrors("an option", 0, 2);
            switch (command) {
                case 0:
                    return;
                case 1:
                    login();
                    break;
                case 2:
                    signUp();
                    break;
            }
        }
    }

    public static void login() throws InterruptedException {
        System.out.println("~~| Login |~~");
        Scanner scanner = new Scanner(System.in);
        String input;
        User logger = null;
        while (true) {
            System.out.print("Username/Email: ");
            input = scanner.nextLine();
            if (Tools.stringIsValid(input) || Tools.validateEmailFormat(input))
                break;
        }

        for (User user : Database.users) {
            if (Objects.equals(user.getUsername(), input) || Objects.equals(user.getEmail(), input)) {
                logger = user;
            }
        }

        if (logger == null) {
            System.out.println("Invalid input: User with this username/email was not found, please check your credentials and try again");
            return;
        }

        while (true) {
            System.out.print("Password: ");
            String password = scanner.next();
            if (logger.validatePassword(password)) {
                System.out.println("-Account Verified-\nWelcome " + logger.getUsername());
                break;
            } else if (Objects.equals(password, "exit"))
                return;
            System.out.println("Invalid input: Authentication failed,Please re-enter your password\nType \"exit\" to leave to menu");

        }

        logger.displayUserPanel();



    }

    public static void signUp() throws InterruptedException {
        System.out.println("~~| Sign Up |~~");
        String username, email;
        Scanner scanner = new Scanner(System.in);
        boolean accountableUsername = true, accountableEmail = true;

        while (true) {
            System.out.print("Username: ");
            username = scanner.nextLine();
            if (Objects.equals(username, "exit")) {
                return;
            }
            if (!Tools.stringIsValid(username)) {
                accountableUsername = false;
            }
            for (User user : Database.users) {
                if (Objects.equals(username, user.getUsername())) {
                    accountableUsername = false;
                    System.out.println("Invalid input: There is an account with this username, please try again\n(Type \"exit\" to exit to the Main Menu");
                    break;
                }
            }
            if (accountableUsername)
                break;
        }


        while (true) {
            System.out.print("Email: ");
            email = scanner.nextLine();
            if (Objects.equals(email, "exit")) {
                return;
            }
            if (!Tools.validateEmailFormat(email)) {
                accountableEmail = false;
            }
            for (User user : Database.users) {
                if (Objects.equals(email, user.getEmail())) {
                    accountableEmail = false;
                    System.out.println("Invalid input: There is an account with this email, please try again\n(Type \"exit\" to exit to the Main Menu");
                    break;
                }
            }
            if (accountableEmail)
                break;
        }


        String password;
        while (true) {
            System.out.print("Password: ");
            password = scanner.nextLine();
            if (Tools.stringIsValid(password))
                break;
        }


        String regex = "^[a-zA-Z]{2,}$";
        String firstName;
        while (true) {
            System.out.print("First name: ");
            firstName = scanner.nextLine();
            if (Pattern.matches(regex, firstName))
                break;
        }
        String lastName;
        while (true) {
            System.out.print("Last name: ");
            lastName = scanner.nextLine();
            if (Pattern.matches(regex, lastName))
                break;
            System.out.print("Invalid input: Entered string should be at least 2 character and only contain alphabets");
        }
        Birthday birthday;
        while (true) {
            birthday = Birthday.getBirthday();
            if (Birthday.validateBirthday(birthday.getYear(), birthday.getMonth(), birthday.getDay()))
                break;

        }

        User newUser = new User(username, password, firstName, lastName, birthday, email);
        Database.users.add(newUser);
        System.out.print("User created successfully");
        newUser.displayUserPanel();
    }

}
