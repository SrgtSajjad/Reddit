package SBU.CS.Account;

public interface AccountManagement {
    public boolean validatePassword(String enteredPassword);
    public void changeUsername(String newUsername);
    public void changePassword(String newPassword);
    public void changeFirstName(String newFirstName);
    public void changeLastName(String newLastName);
    public void changeBirthday(Birthday newBirthday);
    public void changeEmail(String newEmail);
}

