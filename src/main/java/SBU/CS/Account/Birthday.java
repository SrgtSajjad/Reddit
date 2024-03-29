package SBU.CS.Account;

import SBU.CS.Tools;

import javax.tools.Tool;
import java.util.ArrayList;
import java.util.Scanner;

public class Birthday {
    int year;
    int month;
    int day;

    public Birthday(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public static boolean validateBirthday(int year, int month, int day) { // checks if  a date is available
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

    public static Birthday getBirthday() { // gets birthday from user
        int year, month, day;
        Scanner scanner = new Scanner(System.in);

        year = Tools.handleErrors("a year", 1900, 2024);
        month = Tools.handleErrors("a month", 1, 12);
        day = Tools.handleErrors("a day", 1, 31);


        return new Birthday(year, month, day);
    }
}
