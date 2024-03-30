package SBU.CS.Account;

import org.apache.commons.codec.digest.DigestUtils; // for hashing passwords

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Account implements AccountManagement {
    private String username;
    private String password;
    private String email;
    private final UUID accountID;
    private String firstName;
    private String lastName;
    private Birthday birthday;
    private LocalDateTime timeCreated;
    public Account(String username, String password, String firstName, String lastName, Birthday birthday, String email) {
        this.username = username;
        this.email = email;
        this.password = DigestUtils.sha256Hex(password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        timeCreated = LocalDateTime.now();
        accountID = UUID.randomUUID();
    }

    @Override
    public boolean validatePassword(String enteredPassword) {
        return Objects.equals(this.password, DigestUtils.sha256Hex(enteredPassword));
    }

    @Override
    public void changeUsername(String newUsername) {
        this.username = newUsername;
        System.out.println("Username changed successfully");
    }

    @Override
    public void changePassword(String newPassword) {
        this.password = DigestUtils.sha256Hex(newPassword);
        System.out.println("Password changed successfully");

    }

    public String getUsername() {
        return username;
    }

    public UUID getAccountID() {
        return accountID;
    }

    public String getFirstName() {
        return firstName;
    }

    @Override
    public void changeFirstName(String newFirstName) {
        this.firstName = newFirstName;
        System.out.println("First name changed successfully");

    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public void changeLastName(String newLastName) {
        this.lastName = newLastName;
        System.out.println("Last name changed successfully");

    }

    public Birthday getBirthday() {
        return birthday;
    }

    @Override
    public void changeBirthday(Birthday newBirthday) {
        if (Birthday.validateBirthday(newBirthday.year, newBirthday.month, newBirthday.day)) {
            this.birthday = newBirthday;
            System.out.println("Birthday changed successfully");
        }
        else
            System.out.println("Invalid input: Entered date is not available");

    }


    public String getEmail() {
        return email;
    }

    @Override
    public void changeEmail(String newEmail) {
        this.email = newEmail;
        System.out.println("Email changed successfully");

    }

    public int getAge() {
        return LocalDateTime.now().getYear() - birthday.year;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }
}

