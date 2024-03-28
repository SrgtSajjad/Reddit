package SBU.CS.Subreddit;

import SBU.CS.Account.User;
import SBU.CS.Tools;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Post extends Comment {

    private String title;
    private ArrayList<String> flairTags = new ArrayList<>();

    public Post(String title, String text, Subreddit subreddit, User publisher, ArrayList<String> flairTags) {
        super(text, publisher, subreddit);
        this.title = title;
        this.flairTags = flairTags;
        subreddit.posts.addFirst(this); // adds the created post to a subreddit's  post list
    }

    @Override
    public void displayBrief() { // for displaying a short version of the post
        System.out.println("r/" + getSubreddit().title + " - " + Tools.calculateTimePassed(getTimePublished()));
        System.out.println("### " + this.title);

        for (String tag : flairTags) {
            System.out.print("#" + tag + " ");
        }

    }

    @Override
    public void displayComplete(User user) throws InterruptedException {

        Scanner scanner = new Scanner(System.in);
        boolean hasUpVoted, hasDownVoted;
        int command;

        while (true) {
            Tools.clearScreen();
            System.out.println("r/" + getPublisher().getUsername());
            System.out.printf("u/%s - %d/%d/%d at %d:%d\n", getPublisher().getUsername(), getTimePublished().getYear(), getTimePublished().getMonthValue(), getTimePublished().getDayOfMonth(), getTimePublished().getHour(), getTimePublished().getMinute());
            System.out.println("### " + this.title);
            System.out.println("## " + this.getText());
            for (String tag : flairTags) {
                System.out.print("#" + tag + " ");
            }
            System.out.println("Karma: " + getKarma());
            hasUpVoted = this.upVoters.contains(user);
            hasDownVoted = this.downVoters.contains(user);
            System.out.println("\n0. Exit\n1. " + (hasUpVoted ? "Retract Vote" : "Upvote") + "\n2. " + (hasDownVoted ? "Retract Vote" : "Down vote") + "\n3. Comment\n4. View Comments" + (getPublisher() == user ? "\n5. Edit Post" : "") + (getSubreddit().admins.contains(user) ? "\n6. Admin Actions" : ""));
            if (hasUpVoted) {
                System.out.println("(You have up-voted this comment)");
            } else if (hasDownVoted) {
                System.out.println("(You have down-voted this comment)");
            }
            command = Tools.handleErrors("an option", 0, 6);
            switch (command) {
                case 0: // exit
                    return;
                case 1: // retract if user has voted or upvote if it has not
                    if (hasUpVoted) {
                        this.upVoters.remove(user);
                        user.getUpVotedPosts().remove(this);
                        System.out.println("Vote removed successfully");
                    } else {
                        this.upVoters.add(user);
                        user.getUpVotedPosts().add(this);
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
                case 3: // add a comment to the post
                    System.out.println("Enter your comment:");
                    comments.add(new Comment(scanner.nextLine(), user, getSubreddit()));
                    break;
                case 4: // view other comments
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

    @Override
    public void adminActions() {
        // TODO
    }

    @Override
    public void edit() {
        boolean flag = true;
        int command;
        Scanner scanner = new Scanner(System.in);
        while (flag) {
            System.out.println("Editing post: \n0. Cancel\n1. Edit Title\n2. Edit Text\n3. Edit Flair/Tags");
            command = Tools.handleErrors("an option", 0, 3);
            switch (command) {
                case 0:
                    flag = false;
                    break;
                case 1:
                    setTitle(scanner.nextLine());
                    System.out.println("Title edited successfully");
                    break;
                case 2:
                    setText(scanner.nextLine());
                    System.out.println("Text edited successfully");
                    break;
                case 3:
                    this.flairTags = inputTags();
                    System.out.println("Tags edited successfully");
                    break;
            }
        }
    }

    public static ArrayList<String> inputTags() {
        ArrayList<String> tags = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("type \"exit\" when you are done adding tags");
        int i = 0;
        while (!Objects.equals(scanner.nextLine(), "exit")) {
            i++;
            System.out.print(i + ". ");
            tags.add(scanner.nextLine());
        }
        return tags;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public ArrayList<String> getFlairTags() {
        return flairTags;
    }

}
