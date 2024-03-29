package SBU.CS.Account;

import org.apache.commons.codec.digest.DigestUtils; // for hashing passwords

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Account implements AccountManagement {
    private String username;
    private String password;
    private final UUID accountID;
    private String firstName;
    private String lastName;
    private Birthday birthday;
    private LocalDateTime timeCreated;
    public Account(String username, String password, String firstName, String lastName, Birthday birthday) {
        this.username = username;
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
    }

    @Override
    public void changePassword(String newPassword) {
        this.password = DigestUtils.sha256Hex(newPassword);
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

    public void changeFirstName(String newFirstName) {
        this.firstName = newFirstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void changeLastName(String newLastName) {
        this.lastName = newLastName;
    }

    public Birthday getBirthday() {
        return birthday;
    }

    public void changeBirthday(Birthday newBirthday) {
        if (Birthday.validateBirthday(newBirthday.year, newBirthday.month, newBirthday.day)) {
            this.birthday = newBirthday;
            System.out.println("Birthday changed successfully");
        }
        else
            System.out.println("Entered date is not available, please try again");

    }



    public int getAge() {
        return LocalDateTime.now().getYear() - birthday.year;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }
}

