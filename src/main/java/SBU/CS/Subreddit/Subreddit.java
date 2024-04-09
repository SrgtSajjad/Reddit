package SBU.CS.Subreddit;

import SBU.CS.Account.User;
import SBU.CS.Database;
import SBU.CS.Tools;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Subreddit implements Serializable {
    private String title;
    private User creator;
    private ArrayList<User> admins = new ArrayList<>();
    private ArrayList<Post> posts = new ArrayList<>();
    private ArrayList<User> members = new ArrayList<>();
    private ArrayList<User> bannedUsers = new ArrayList<>();
    private String explanation;
    private LocalDateTime timeCreated;

    public Subreddit(String title, String explanation, User creator) {
        this.title = title;
        this.explanation = explanation;
        this.creator = creator;
        admins.add(creator); // automatically add the creator to the subreddit's admin list
        members.add(creator);
        timeCreated = LocalDateTime.now();
        Database.subreddits.add(this); // add the created subreddit to the database
    }

    public void displayBrief() {
        System.out.println("r/" + title);
        System.out.println(members.size() + " members");
        System.out.println(explanation);
    }

    public void displayComplete() {
        System.out.println("r/" + title + " - " + members.size() + " members");
        System.out.printf("created %d/%d/%d\n", timeCreated.getYear(), timeCreated.getMonthValue(), timeCreated.getDayOfMonth());
        System.out.println(explanation);
    }

    public void viewUserActions(User user) throws InterruptedException {

        Scanner scanner = new Scanner(System.in);
        boolean hasJoined;
        int command, i;

        while (true) {
            Tools.clearScreen();
            displayComplete();
            hasJoined = this.members.contains(user);
            if (hasJoined) {
                System.out.println("(joined" + (admins.contains(user) ? " as admin" : "") + ")");
            }
            System.out.println("\n0. Exit\n1. " + (hasJoined ? "Leave Subreddit" : "Join Subreddit") + "\n2. View Posts" + (creator == user ? "\n3. Edit Subreddit" : ""));
            command = Tools.handleErrors("an option", 0, (creator == user ? 3 : 2));
            switch (command) {
                case 0: // exit
                    return;
                case 1: // join if user hasn't joined or leave if they have
                    if (hasJoined) {
                        this.members.remove(user);
                        user.getJoinedSubreddits().remove(this);
                        this.admins.remove(user);
                        System.out.println("Subreddit leaved successfully");
                    } else {
                        this.members.add(user);
                        user.getJoinedSubreddits().add(this);
                        System.out.println("Subreddit joined successfully");
                    }
                    break;
                case 2: // view the subreddit's posts
                    i = 0;
                    System.out.println("0. Exit");
                    for (Post post : posts) {
                        i++;
                        System.out.println(i + ". ");
                        post.displayBrief();
                    }

                    command = Tools.handleErrors("a post", 0, posts.size());
                    if (command == 0) {
                        break;
                    }
                    posts.get(command - 1).viewUserActions(user);
                    break;
                case 3: // edit subreddit if user is the Creator 
                    if (creator == user)
                        editSubreddit();
                    else
                        System.out.println("\u001B[31m Invalid input \u001B[0m: Please enter a valid number");
                    break;

            }
            sleep(200);
        }

    }

    private void editSubreddit() throws InterruptedException {
        boolean flag = true;
        int command;
        Scanner scanner = new Scanner(System.in);
        while (flag) {
            System.out.println("Editing Subreddit: \n0. Cancel\n1. Edit Title\n2. Edit Explanation\n3. Edit Admins");
            command = Tools.handleErrors("an option", 0, 3);
            switch (command) {
                case 0:
                    flag = false;
                    break;
                case 1:
                    Boolean available = true;
                    String title = scanner.nextLine();
                    for (Subreddit subreddit : Database.subreddits) {
                        if (Objects.equals(subreddit.getTitle(), title)) {
                            System.out.print("Invalid title: There is already a subreddit with this name, please try again");
                            available = false;
                            break;
                        }
                    }
                    if (available) {
                        this.title = title;
                        System.out.println("Title edited successfully");
                    }
                    break;
                case 2:
                    explanation = scanner.nextLine();
                    System.out.println("Explanation edited successfully");
                    break;
                case 3:
                    editAdmins();
                    break;
            }
            sleep(200);
        }
    }

    private void editAdmins() throws InterruptedException {
        boolean flag = true;
        int command, i;
        Scanner scanner = new Scanner(System.in);
        while (flag) {
            System.out.println("Editing Admins: \n0. Exit\n1. View Admins List\n2. Dismiss an admin\n3. Appoint New Admin");
            command = Tools.handleErrors("an option", 0, 2);
            switch (command) {
                case 0:
                    flag = false;
                    break;
                case 1:
                    i = 0;
                    for (User user : admins) {
                        i++;
                        System.out.println(i + ". " + user.getUsername());
                    }
                    break;
                case 2:
                    System.out.println("0. Exit");
                    i = 0;
                    for (User user : admins) {
                        i++;
                        System.out.println(i + ". " + user.getUsername());
                    }
                    command = Tools.handleErrors("an admin", 0, admins.size());
                    if (command == 0)
                        break;
                    User selectedAdmin = admins.get(command - 1);
                    System.out.println("Confirm dismiss for " + selectedAdmin.getUsername() + "\n1. No\n2. Yes");
                    command = Tools.handleErrors("an option", 1, 2);
                    if (command == 2) {
                        admins.remove(selectedAdmin);
                        System.out.println("Admin removed successfully");
                    }
                    break;
                case 3:
                    System.out.println("0. Exit");
                    i = 0;
                    for (User user : members) {
                        i++;
                        System.out.println(i + ". " + user.getUsername());
                    }
                    command = Tools.handleErrors("a user", 0, members.size());
                    if (command == 0)
                        break;
                    User selectedUser = members.get(command - 1);
                    if (admins.contains(selectedUser)) {
                        System.out.println("User is already an admin");
                        break;
                    }
                    System.out.println("Confirm appointing of " + selectedUser.getUsername() + "\n1. No\n2. Yes");
                    command = Tools.handleErrors("an option", 1, 2);
                    if (command == 2) {
                        admins.add(selectedUser);
                        System.out.println("Admin appointed successfully");
                    }
                    break;
            }
            sleep(200);
        }


    }


    public String getTitle() {
        return title;
    }

    public User getCreator() {
        return creator;
    }

    public ArrayList<User> getAdmins() {
        return admins;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public ArrayList<User> getBannedUsers() {
        return bannedUsers;
    }
}
