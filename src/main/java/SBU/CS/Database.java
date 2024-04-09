package SBU.CS;

import SBU.CS.Account.User;
import SBU.CS.Subreddit.Subreddit;

import java.io.*;
import java.util.ArrayList;

public class Database {
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<Subreddit> subreddits = new ArrayList<>();

    public static void serializeClasses() {
        try {
            // Open a file for writing
            FileOutputStream fileOut = new FileOutputStream("Users.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            // Write the list of users to the file
            out.writeObject(users);

            // Close the streams
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // Open a file for writing
            FileOutputStream fileOut = new FileOutputStream("Subreddits.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            // Write the list of subreddit to the file
            out.writeObject(subreddits);

            // Close the streams
            out.close();
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deserializeClasses() {
        try {
            // Open the file for reading
            FileInputStream fileIn = new FileInputStream("Users.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            // Read the serialized objects from the file
            Database.users = (ArrayList<User>) in.readObject();

            // Close the streams
            in.close();
            fileIn.close();


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            // Open the file for reading
            FileInputStream fileIn = new FileInputStream("Subreddits.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            // Read the serialized objects from the file
            Database.subreddits = (ArrayList<Subreddit>) in.readObject();

            // Close the streams
            in.close();
            fileIn.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

