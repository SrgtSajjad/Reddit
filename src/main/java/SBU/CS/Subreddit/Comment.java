package SBU.CS.Subreddit;

import SBU.CS.Account.User;
import SBU.CS.Tools;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Comment {
    private Comment mainCommentOrPost;
    private String text;
    private User publisher;
    private LocalDateTime timePublished;
    ArrayList<Comment> comments = new ArrayList<>();
    ArrayList<User> upVoters = new ArrayList<>();
    ArrayList<User> downVoters = new ArrayList<>();

    public Comment(String text, User publisher) {
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

    public void displayComplete(User user) {
        System.out.printf("u/%s - %d/%d/%d at %d:%d\n", publisher.getUsername(), timePublished.getYear(), timePublished.getMonthValue(), timePublished.getDayOfMonth(), timePublished.getHour(), timePublished.getMinute());
        System.out.println("## " + this.text);
        System.out.println("Karma: " + getKarma());

        Scanner scanner = new Scanner(System.in);
        boolean hasUpVoted = this.upVoters.contains(user);
        boolean hasDownVoted = this.downVoters.contains(user);

        System.out.println("\n0. Exit\n1. " + (hasUpVoted ? "Retract Vote" : "Upvote") + "\n2. " + (hasDownVoted ? "Retract Vote" : "Down vote") + "\n3. reply\n4. View replies");
        if (hasUpVoted) {
            System.out.println("(You have up-voted this comment)");
        } else if (hasDownVoted) {
            System.out.println("(You have down-voted this comment)");
        }
        int command = Tools.handleErrors("an option", 0, 4);
        switch (command) {
            case 0:
                return;
            case 1:
                if (hasUpVoted) {
                    this.upVoters.remove(user);
                    System.out.println("Vote removed successfully");
                } else {
                    this.upVoters.add(user);
                    System.out.println("Up-voted successfully");
                }
                break;
            case 2:
                if (hasDownVoted) {
                    this.downVoters.remove(user);
                    System.out.println("Vote removed successfully");
                } else {
                    this.downVoters.add(user);
                    System.out.println("Down-voted successfully");
                }
                break;
            case 3:
                System.out.println("Enter your comment:");
                Comment newComment = new Comment(scanner.nextLine(), user);
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
}
