package SBU.CS.Subreddit;

import SBU.CS.Account.User;
import SBU.CS.Tools;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Post extends Comment {

    String title;
    Subreddit subreddit;
    ArrayList<String> flairTags = new ArrayList<>();

    public Post(String title, String text, Subreddit subreddit, User publisher, ArrayList<String> flairTags) {
        super(text, publisher);
        this.title = title;
        this.text = text;
        this.subreddit = subreddit;
        this.flairTags = flairTags;
        subreddit.posts.addFirst(this); // adds the created post to a subreddit's  post list
    }

    @Override
    public void displayBrief() { // for displaying a short version of the post
        System.out.println("r/" + subreddit.title + " - " + Tools.calculateTimePassed (timePublished));
        System.out.println("### " + this.title);
        System.out.println("## " + this.text);

        for (String tag: flairTags) {
            System.out.print("#" + tag + " ");
        }

    }

    @Override
    public void displayComplete(User user) {
        System.out.println("r/" + publisher.getUsername());
        System.out.printf("u/%s - %d/%d/%d at %d:%d\n",publisher.getUsername(), timePublished.getYear(), timePublished.getMonthValue(), timePublished.getDayOfMonth(), timePublished.getHour(), timePublished.getMinute());
        System.out.println("### " + this.title);
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
                comments.add(new Comment(scanner.nextLine(), user));
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


}
