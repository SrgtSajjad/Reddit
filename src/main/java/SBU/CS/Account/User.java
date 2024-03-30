package SBU.CS.Account;

import SBU.CS.Notification;
import SBU.CS.Subreddit.Comment;
import SBU.CS.Subreddit.Post;
import SBU.CS.Subreddit.Subreddit;
import SBU.CS.Tools;

import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class User extends Account {
    private ArrayList<Post> posts = new ArrayList<>();
    private ArrayList<Comment> comments = new ArrayList<>();
    private ArrayList<Post> upVotedPosts = new ArrayList<>();
    private ArrayList<Comment> upVotedComments = new ArrayList<>();
    private ArrayList<Subreddit> joinedSubreddits = new ArrayList<>();
    private ArrayList<Notification> notifications = new ArrayList<>();

    public User(String username, String password, String firstName, String lastName, Birthday birthday) {
        super(username, password, firstName, lastName, birthday);
    }

    public int getTotalKarma() {
        int karma = 0;
        for (Post post : posts) {
            karma += post.getKarma();
        }

        for (Comment comment : comments) {
            karma += comment.getKarma();
        }

        return karma;
    }

    public int getPostKarma() {
        int karma = 0;
        for (Post post : posts) {
            karma += post.getKarma();
        }


        return karma;
    }

    public int getCommentKarma() {
        int karma = 0;

        for (Comment comment : comments) {
            karma += comment.getKarma();
        }

        return karma;
    }


    public ArrayList<Post> getPosts() {
        return posts;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public ArrayList<Post> getUpVotedPosts() {
        return upVotedPosts;
    }

    public ArrayList<Comment> getUpVotedComments() {
        return upVotedComments;
    }

    public ArrayList<Subreddit> getJoinedSubreddits() {
        return joinedSubreddits;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void myProfile() throws InterruptedException {
        int i;
        boolean flag = true;
        while (flag) {
            Tools.clearScreen();
            System.out.println("~~| My Profile |~~");
            System.out.println("### " + getUsername());
            System.out.printf("u/%s - %d karma - %s %d, %d\n", getUsername(), getTotalKarma(), getTimeCreated().getMonth(), getTimeCreated().getDayOfMonth(), getTimeCreated().getYear());

            System.out.println("""
                                        
                    0. Exit
                    1. Edit
                    2. Posts
                    3. Comments
                    4. About
                    """);
            int command = 0;
            switch (command) {
                case 0:
                    flag = false;
                    break;
                case 1:
                    editProfile();
                    break;
                case 2:
                    i = 0;
                    System.out.println("0. Exit");
                    for (Post post : posts) {
                        i++;
                        System.out.println(i + ". ");
                        post.displayBrief();
                    }
                    command = Tools.handleErrors("an option", 0, posts.size());
                    if (command != 0)
                        posts.get(command - 1).viesUserActions(this);
                    break;
                case 3:
                    i = 0;
                    System.out.println("0. Exit");
                    for (Comment comment : comments) {
                        i++;
                        System.out.println(i + ". ");
                        comment.displayBrief();
                    }
                    command = Tools.handleErrors("an option", 0, posts.size());
                    if (command != 0)
                        comments.get(command - 1).viesUserActions(this);
                    break;
                case 4:
                    System.out.printf("Post karma: %d | Comment karma: %d \n", getPostKarma(), getCommentKarma());
                    break;
            }
            sleep(200);
        }
    }

    private void editProfile() throws InterruptedException {
        boolean flag = true;
        Scanner scanner = new Scanner(System.in);
        while (flag) {
            Tools.clearScreen();
            System.out.println("~~| Edit Profile |~~");
            System.out.println("Username: " + getUsername());
            System.out.println("First Name: " + getFirstName());
            System.out.println("Last Name: " + getLastName());
            System.out.printf("Birthday: %d/%d/%d", getBirthday().year, getBirthday().month, getBirthday().day);

            System.out.println("""

                    0. Exit
                    1. Change Username
                    2. Change Password
                    3. Change First Name
                    4. Change Last Name
                    5. change Birthday""");
            int command = Tools.handleErrors("an option", 0, 5);
            String input;
            scanner.nextLine();
            switch (command) {
                case 0:
                    flag = false;
                    break;
                case 1:
                    System.out.print("New Username: ");
                    input = scanner.nextLine();
                    if (Tools.verifyChanges(input))
                        changeUsername(input);
                    break;
                case 2:
                    System.out.print("New Password: ");
                    input = scanner.nextLine();
                    if (Tools.verifyChanges(input))
                        changePassword(input);
                    break;
                case 3:
                    System.out.print("New First Name: ");
                    input = scanner.nextLine();
                    if (Tools.verifyChanges(input))
                        changeFirstName(input);
                    break;
                case 4:
                    System.out.print("New Last Name: ");
                    input = scanner.nextLine();
                    if (Tools.verifyChanges(input))
                        changeLastName(input);
                    break;
                case 5:
                    changeBirthday(Birthday.getBirthday());
            }
            sleep(200);
        }
    }


    public void getUserPanel() throws InterruptedException { // TODO
        Tools.clearScreen();
        boolean flag = true;
        while (flag) {
            System.out.println("""
                    ~~| Your Panel |~~
                                        
                    0. Logout
                    1. My Profile
                    2. Create a Post
                    3. Create a Community
                    4. Timeline
                    5. Communities
                    6. Search""");
            int command = Tools.handleErrors("an option", 0, 6);
            switch (command) {
                case 0:
                    flag = false;
                    break;
                case 1:
                    myProfile();
                    break;
            }
        }
    }
}
