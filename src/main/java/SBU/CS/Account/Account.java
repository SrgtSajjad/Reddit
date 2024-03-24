package SBU.CS.Account;

import org.apache.commons.codec.digest.DigestUtils;
import java.util.Objects;
import java.util.UUID;

public class Account implements AccountManagement {
    private String username;
    private String password;
    private final UUID accountID;
    private final String firstName;
    private final String lastName;
    private final Birthday birthday;
    private final int age;

    public Account(String username, String password, String firstName, String lastName, Birthday birthday) {
        this.username = username;
        this.password = DigestUtils.sha256Hex(password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        age = 2024 - birthday.year;
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
        this.password = newPassword;
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

    public String getLastName() {
        return lastName;
    }

    public Birthday getBirthday() {
        return birthday;
    }

    public int getAge() {
        return age;
    }
}

