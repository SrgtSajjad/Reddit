package SBU.CS.Subreddit;

import SBU.CS.Account.User;
import SBU.CS.Notification;
import SBU.CS.Tools;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Post extends Comment {

    private String title;
    private ArrayList<String> flairTags = new ArrayList<>();


    public Post(String title, String text, Subreddit subreddit, User publisher, ArrayList<String> flairTags) {
        super(text, publisher, subreddit, null);
        this.title = title;
        this.flairTags = flairTags;
        subreddit.getPosts().addFirst(this); // adds the created post to a subreddit's  post list
    }

    @Override
    public void displayBrief() { // for displaying a short version of the post
        System.out.println("r/" + getSubreddit().getTitle() + " - " + Tools.calculateTimePassed(getTimePublished()));
        System.out.println("Title: " + this.title);

        for (String tag : flairTags) {
            System.out.print("#" + tag + " ");
        }
        System.out.println();

    }

    @Override
    public void displayComplete(User user) {
        System.out.println("r/" + getSubreddit().getTitle());
        System.out.printf("u/%s - %d/%d/%d at %d:%d\n", getPublisher().getUsername(), getTimePublished().getYear(), getTimePublished().getMonthValue(), getTimePublished().getDayOfMonth(), getTimePublished().getHour(), getTimePublished().getMinute());
        System.out.println("Title: " + this.title);
        System.out.println("Text: " + this.getText());
        for (String tag : flairTags) {
            System.out.print("#" + tag + " ");
        }
        System.out.println("Karma: " + getKarma());
    }

    @Override
    public void viewUserActions(User user) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        boolean hasUpVoted, hasDownVoted, hasSaved;
        int command;

        while (true) {
            Tools.clearScreen();
            displayComplete(user);
            hasUpVoted = this.upVoters.contains(user);
            hasDownVoted = this.downVoters.contains(user);
            hasSaved = user.getSavedPosts().contains(this);
            System.out.println("\n0. Exit\n1. " + Tools.GREEN_COLOR + (hasUpVoted ? "Retract Vote" : "Upvote") + Tools.RESET_COLOR + "\n2. " + Tools.RED_COLOR + (hasDownVoted ? "Retract Vote" : "Downvote") + Tools.RESET_COLOR + "\n3. Comment\n4. View Comments\n5. " + (hasSaved ? "Un-Save Post" : "Save Post") + (getPublisher() == user ? "\n6. Edit Post" : "") + (getSubreddit().getAdmins().contains(user) ? "\n7. Admin Actions" : ""));
            if (hasUpVoted) {
                System.out.println("(You have up-voted this comment)");
            } else if (hasDownVoted) {
                System.out.println("(You have down-voted this comment)");
            }
            command = Tools.handleErrors("an option", 0, (getSubreddit().getAdmins().contains(user) ? 7 : (user == getPublisher() ? 6 : 5)));
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
                        this.downVoters.remove(user);
                        user.getUpVotedPosts().addFirst(this);
                        getPublisher().getNotifications().addFirst(new Notification("Post up-voted", "Your post in the subreddit: " + getSubreddit().getTitle() + " with title: " + title + ", was up-voted"));
                        System.out.println("Up-voted successfully");
                    }
                    break;
                case 2: // retract if user has voted or downvote if it has not
                    if (hasDownVoted) {
                        this.downVoters.remove(user);
                        System.out.println("Vote removed successfully");
                    } else {
                        this.downVoters.add(user);
                        this.upVoters.remove(user);
                        user.getUpVotedPosts().remove(this);
                        getPublisher().getNotifications().addFirst(new Notification("Post down-voted", "Your post in the subreddit: " + getSubreddit().getTitle() + " with title: " + title + ", was down-voted"));
                        System.out.println("Down-voted successfully");
                    }
                    break;
                case 3: // add a comment to the post
                    if (getSubreddit().getBannedUsers().contains(user)) {
                        System.out.println("You have been banned from this subreddit and you can't post or comment in it");
                    } else {
                        System.out.println("Enter your comment:");
                        getComments().add(new Comment(scanner.nextLine(), user, getSubreddit(), this));
                    }
                    break;
                case 4: // view other comments
                    System.out.println("\n0. Exit");
                    int i = 0;
                    for (Comment comment : getComments()) {
                        i++;
                        System.out.println(i + ". ");
                        comment.displayBrief();
                    }

                    command = Tools.handleErrors("an option", 0, getComments().size());
                    if (command == 0) {
                        return;
                    } else {
                        getComments().get(command - 1).viewUserActions(user);
                    }
                    break;
                case 5:
                    if (hasSaved) {
                        user.getSavedPosts().remove(this);
                        System.out.println("Post un-saved successfully");
                    }
                    else {
                        user.getSavedPosts().add(this);
                        System.out.println("Post saved successfully");
                    }
                    break;
                case 6: // edit post if it belongs to the user
                    if (getPublisher() == user)
                        edit();
                    else
                        System.out.println("\u001B[31m Invalid input \u001B[0m: Please enter a valid number");
                    break;
                case 7: // open admin actions if user is an admin
                    if (getSubreddit().getAdmins().contains(getPublisher()) && getSubreddit().getCreator() != user) {
                        System.out.println("Publisher of this post is an admin, admin actions is not available for this post");
                    } else if (getSubreddit().getAdmins().contains(user))
                        viewAdminActions(user);
                    else
                        System.out.println("\u001B[31m Invalid input \u001B[0m: Please enter a valid number");
                    break;
            }
            sleep(500);
        }
    }

    @Override
    public void viewAdminActions(User user) throws InterruptedException {
        int command;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Admin Actions: \n0. Cancel\n1. Delete Post\n2. Ban publisher from subreddit");
        command = Tools.handleErrors("an option", 0, 2);
        switch (command) {
            case 0:
                break;
            case 1:
                System.out.printf("Confirm delete of the post: \"%s\"\n1. No\n2. Yes\n", title);
                command = Tools.handleErrors("an option", 1, 2);
                if (command == 2) {
                    getSubreddit().getPosts().remove(this);
                    getPublisher().getNotifications().addFirst(new Notification("Post Deletion", "Your post in the subreddit: " + getSubreddit().getTitle() + " with title: " + title + ", was deleted by an admin"));
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
                    getPublisher().getNotifications().addFirst(new Notification("Banned from subreddit", "Due to your post in the subreddit: " + getSubreddit().getTitle() + " with title: " + title + ", you have been banned from this subreddit"));
                    System.out.println("User banned and post deleted successfully");

                }
                break;
        }
        sleep(200);

    }

    @Override
    public void edit() throws InterruptedException {
        boolean flag = true;
        int command;
        Scanner scanner = new Scanner(System.in);
        while (flag) {
            System.out.println("Editing post: \n0. Cancel\n1. Edit Title\n2. Edit Text\n3. Edit Flair/Tags\n4. Delete Post");
            command = Tools.handleErrors("an option", 0, 4);
            switch (command) {
                case 0:
                    flag = false;
                    break;
                case 1:
                    System.out.print("New Title: ");
                    setTitle(scanner.nextLine());
                    System.out.println("Title edited successfully");
                    break;
                case 2:
                    System.out.print("New Text: ");
                    setText(scanner.nextLine());
                    System.out.println("Text edited successfully");
                    break;
                case 3:
                    this.flairTags = inputTags();
                    System.out.println("Tags edited successfully");
                    break;
                case 4:
                    System.out.printf("Confirm delete of the post: \"%s\"\n1. No\n2. Yes\n", title);
                    command = Tools.handleErrors("an option", 1, 2);
                    if (command == 2) {
                        getSubreddit().getPosts().remove(this);
                        this.getPublisher().getPosts().remove(this);
                        System.out.println("Post deleted successfully");
                    }
                    break;

            }
            sleep(200);
        }
    }

    public static ArrayList<String> inputTags() {
        ArrayList<String> tags = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("type \"exit\" when you are done adding tags");
        int i = 0;
        boolean flag = true;
        while (flag) {
            i++;
            System.out.print(i + ". ");
            String input = scanner.nextLine();
            if (Objects.equals(input, "exit"))
                break;
            else
                tags.add(input);

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
