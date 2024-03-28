package SBU.CS.Subreddit;

import SBU.CS.Account.User;
import SBU.CS.Tools;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Comment {
    private Comment mainCommentOrPost;
    private String text;
    private User publisher;
    private LocalDateTime timePublished;
    private Subreddit subreddit;
    ArrayList<Comment> comments = new ArrayList<>();
    ArrayList<User> upVoters = new ArrayList<>();
    ArrayList<User> downVoters = new ArrayList<>();

    public Comment(String text, User publisher, Subreddit subreddit) {
        this.subreddit =subreddit;
        this.text = text;
        this.publisher = publisher;
        timePublished = LocalDateTime.now(); // for sorting user's timeline based on time

    }

    public void setMainCommentOrPost(Comment mainCommentOrPost) {
        this.mainCommentOrPost = mainCommentOrPost;
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
        Scanner scanner = new Scanner(System.in);
        boolean hasUpVoted, hasDownVoted;
        int command;

        while (true) {
            System.out.printf("u/%s - %d/%d/%d at %d:%d\n", publisher.getUsername(), timePublished.getYear(), timePublished.getMonthValue(), timePublished.getDayOfMonth(), timePublished.getHour(), timePublished.getMinute());
            System.out.println("## " + this.text);
            System.out.println("Karma: " + getKarma());
            hasUpVoted = this.upVoters.contains(user);
            hasDownVoted = this.downVoters.contains(user);
            System.out.println("\n0. Exit\n1. " + (hasUpVoted ? "Retract Vote" : "Upvote") + "\n2. " + (hasDownVoted ? "Retract Vote" : "Down vote") + "\n3. Reply\n4. View Replies" + (user.getComments().contains(this) ? "\n5. Edit Post" : "") + (getSubreddit().admins.contains(user) ? "\n6. Admin Actions" : ""));
            if (hasUpVoted) {
                System.out.println("(You have up-voted this comment)");
            } else if (hasDownVoted) {
                System.out.println("(You have down-voted this comment)");
            }
            command = Tools.handleErrors("an option", 0, 6);
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
                        System.out.println("Up-voted successfully");
                    }
                    break;
                case 2: // retract if user has voted or downvote if it has not
                    if (hasDownVoted) {
                        this.downVoters.remove(user);
                        System.out.println("Vote removed successfully");
                    } else {
                        this.downVoters.add(user);
                        System.out.println("Down-voted successfully");
                    }
                    break;
                case 3:
                    System.out.println("Enter your comment:"); // add a comment to the post
                    Comment newComment = new Comment(scanner.nextLine(), user, subreddit);
                    user.getComments().add(newComment);
                    comments.add(newComment);
                    newComment.setMainCommentOrPost(this);
                    break;
                case 4:
                    System.out.println("\n0. Exit");
                    int i = 0;
                    for (Comment comment : comments) {
                        i++;
                        System.out.println(i + ". ");
                        comment.displayBrief();
                    }

                    command = Tools.handleErrors("an option", 0, comments.size());
                    if (command == 0) {
                        return;
                    } else {
                        comments.get(command - 1).displayComplete(user);
                    }
                    break;
                case 5: // edit post if it belongs to the user
                    if (getPublisher() == user)
                        edit();
                    else
                        System.out.println("Invalid input: Please enter a valid number");
                    break;
                case 6:
                    if (getSubreddit().admins.contains(user))
                        adminActions();
                    else
                        System.out.println("Invalid input: Please enter a valid number");
                    break;
            }
            sleep(200);
        }
    }

    public void adminActions() {
        boolean flag = true;
        int command;
        Scanner scanner = new Scanner(System.in);
        while (flag) {
            System.out.println("Admin actions: \n0. Cancel \n1. Delete post \n2. Ban publisher");
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

    public void edit() {
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

    public Comment getMainCommentOrPost() {
        return mainCommentOrPost;
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
