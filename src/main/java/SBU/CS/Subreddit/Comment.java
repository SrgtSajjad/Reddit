package SBU.CS.Subreddit;

import SBU.CS.Account.User;
import SBU.CS.Notification;
import SBU.CS.Tools;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import static java.lang.Thread.sleep;

public class Comment {
    private Post post;
    private String text;
    private User publisher;
    private LocalDateTime timePublished;
    private Subreddit subreddit;
    ArrayList<User> upVoters = new ArrayList<>();
    ArrayList<User> downVoters = new ArrayList<>();
    private UUID ID;
    public Comment(String text, User publisher, Subreddit subreddit, Post post) {
        this.subreddit = subreddit;
        this.text = text;
        this.publisher = publisher;
        this.post = post;
        ID = UUID.randomUUID();
        timePublished = LocalDateTime.now(); // for sorting user's timeline based on time
    }

    public int getKarma() {
        return upVoters.size() - downVoters.size();
    }

    public void displayBrief() {
        System.out.println("u/" + publisher.getUsername() + " - " + Tools.calculateTimePassed(timePublished));
        System.out.println("## " + this.text);
        System.out.println("Karma: " + getKarma());
    }

    public void displayComplete(User user) throws InterruptedException {
        System.out.printf("u/%s - %d/%d/%d at %d:%d\n", publisher.getUsername(), timePublished.getYear(), timePublished.getMonthValue(), timePublished.getDayOfMonth(), timePublished.getHour(), timePublished.getMinute());
        System.out.println("## " + this.text);
        System.out.println("Karma: " + getKarma());

    }

    public void viewUserActions(User user) throws InterruptedException {

        boolean hasUpVoted, hasDownVoted;
        int command;

        while (true) {
            Tools.clearScreen();
            displayComplete(user);
            hasUpVoted = this.upVoters.contains(user);
            hasDownVoted = this.downVoters.contains(user);
            System.out.println("\n0. Exit\n1. " + (hasUpVoted ? "Retract Vote" : "Upvote") + "\n2. " + (hasDownVoted ? "Retract Vote" : "Down vote") + (user.getComments().contains(this) ? "\n3. Edit Comment" : "") + (getSubreddit().getAdmins().contains(user) ? "\n4. Admin Actions" : ""));
            if (hasUpVoted) {
                System.out.println("(You have up-voted this comment)");
            } else if (hasDownVoted) {
                System.out.println("(You have down-voted this comment)");
            }
            command = Tools.handleErrors("an option", 0, (subreddit.getAdmins().contains(user) ? 4 : (user == publisher ? 3 : 2)));
            switch (command) {
                case 0:
                    return;
                case 1: // retract if user has voted or upvote if it has not
                    if (hasUpVoted) {
                        this.upVoters.remove(user);
                        user.getUpVotedComments().remove(this);
                        System.out.println("Vote removed successfully");
                    } else {
                        this.upVoters.add(user);
                        user.getUpVotedComments().add(this);
                        getPublisher().getNotifications().addFirst(new Notification("Comment up-voted", "Your comment in the subreddit: " + getSubreddit().getTitle() + " for post with title: " + post.getTitle() + ", was up-voted"));
                        System.out.println("Up-voted successfully");
                    }
                    break;
                case 2: // retract if user has voted or downvote if it has not
                    if (hasDownVoted) {
                        this.downVoters.remove(user);
                        System.out.println("Vote removed successfully");
                    } else {
                        this.downVoters.add(user);
                        getPublisher().getNotifications().addFirst(new Notification("Comment down-voted", "Your comment in the subreddit: " + getSubreddit().getTitle() + " for post with title: " + post.getTitle() + ", was down-voted"));
                        System.out.println("Down-voted successfully");
                    }
                    break;
                case 3: // edit post if it belongs to the user
                    if (getPublisher() == user)
                        edit();
                    else
                        System.out.println("Invalid input: Please enter a valid number");
                    break;
                case 4: // open admin actions if user is an admin
                    if (getSubreddit().getAdmins().contains(getPublisher()) && getSubreddit().getCreator() != user) {
                        System.out.println("Publisher of this comment is an admin, admin actions is not available for this comment");
                    } else if (getSubreddit().getAdmins().contains(user))
                        viewAdminActions(user);
                    else
                        System.out.println("Invalid input: Please enter a valid number");
                    break;
            }
            sleep(200);
        }
    }

    public void viewAdminActions(User user) throws InterruptedException {
        int command;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Admin Actions: \n0. Cancel\n1. Delete Comment\n2. Ban publisher from subreddit");
        command = Tools.handleErrors("an option", 0, 3);
        switch (command) {
            case 0:
                break;
            case 1:
                System.out.println("Confirm delete of the comment\n1. No\n2. Yes\n");
                command = Tools.handleErrors("an option", 1, 2);
                if (command == 2) {
                    getSubreddit().getPosts().remove(this);
                    getPublisher().getNotifications().addFirst(new Notification("Comment Deletion", "Your comment in the subreddit: " + getSubreddit().getTitle() + " for post with title: " + post.getTitle() + ", was deleted by an admin"));
                    System.out.println("Post deleted successfully");
                }
                break;
            case 2:
                System.out.printf("Confirm ban of the publisher: \"%s\"\n1. No\n2. Yes\n", getPublisher().getUsername());
                command = Tools.handleErrors("an option", 1, 2);
                if (command == 2) {
                    getSubreddit().getBannedUsers().add(getPublisher());
                    getSubreddit().getAdmins().remove(getPublisher());
                    getSubreddit().getPosts().remove(this);
                    getPublisher().getNotifications().addFirst(new Notification("Banned from subreddit", "Due to your comment in the subreddit: " + getSubreddit().getTitle() + " for the post with title: " + post.getTitle() + ", you have been banned from this subreddit"));
                    System.out.println("User banned and post deleted successfully");
                }
                break;
        }
        sleep(200);
    }

    public void edit() throws InterruptedException {
        boolean flag = true;
        int command;
        Scanner scanner = new Scanner(System.in);
        while (flag) {
            System.out.println("Editing comment: \n0. Cancel \n1. Edit Text");
            command = Tools.handleErrors("an option", 0, 2);
            switch (command) {
                case 0:
                    flag = false;
                    break;
                case 1:
                    setText(scanner.nextLine());
                    System.out.println("Text edited successfully");
                    break;
            }
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getPublisher() {
        return publisher;
    }

    public LocalDateTime getTimePublished() {
        return timePublished;
    }

    public Subreddit getSubreddit() {
        return subreddit;
    }
}
