package SBU.CS.Account;

public class Birthday {
    int year;
    int month;
    int day;

    public Birthday(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public boolean validateBirthday() {
        boolean isLeapYear = year % 4 == 0;
        if (day <= 0)
            return false;

        switch (month) {
            case 1, 3, 5, 7, 8, 10, 12:
                if (day > 31)
                    return false;
                break;
            case 2:
                if (isLeapYear && day > 29)
                    return false;
                else if (day > 28)
                    return false;
                break;
            case 4, 6, 9, 11:
                if (day > 30)
                    return false;
                break;
            default:
                return false;
        }

        return true;
    }
}
