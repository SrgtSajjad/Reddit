package SBU.CS.Account;

import java.time.LocalDate;

public interface AccountManagement {
    public boolean validatePassword(String enteredPassword);
    public void changeUsername(String newUsername);
    public void changePassword(String newPassword);
    public void changeFirstName(String newFirstName);
    public void changeLastName(String newLastName);
    public void changeBirthday(LocalDate newBirthday);
    public void changeEmail(String newEmail);
}

