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

            // Write the array of students to the file
            out.writeObject(users);

            // Close the streams
            out.close();
            fileOut.close();

            System.out.println("Students have been serialized and saved to students.ser");
        } catch(IOException e) {
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

            // Print the deserialized students
            for (User user : Database.users) {
                System.out.println("Name: " + user.getUsername());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
