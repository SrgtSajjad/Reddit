package SBU.CS.Account;

public interface AccountManagement {
    public boolean validatePassword(String enteredPassword);
    public void changeUsername(String newUsername);
    public void changePassword(String newPassword);
}

