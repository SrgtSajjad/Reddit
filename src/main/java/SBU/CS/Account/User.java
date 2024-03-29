package SBU.CS.Account;

import SBU.CS.Notification;
import SBU.CS.Subreddit.Comment;
import SBU.CS.Subreddit.Post;
import SBU.CS.Subreddit.Subreddit;
import SBU.CS.Tools;

import java.util.ArrayList;
import java.util.Scanner;

public class User extends Account {
    private ArrayList<Post> posts = new ArrayList<>();
    private ArrayList<Post> upVotedPosts = new ArrayList<>();
    private ArrayList<Comment> upVotedComments = new ArrayList<>();

    private ArrayList<Comment> comments = new ArrayList<>();
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

    public void panel() { // TODO
        Tools.clearScreen();
        boolean flag = true;
        while (flag) {
            System.out.println("""
                    ~~| Your Panel |~~
                    
                    0. Logout
                    1. My Profile
                    4. Create a Post
                    3. Create a Community
                    4. Timeline
                    5. Communities
                    6. Search""");
            Scanner scanner = new Scanner(System.in);
            int command = 0;
            switch (command) {
                case 0:
                    flag = false;
                    break;
                default:
                    System.out.println("Error: Option is not available, please choose from the list above");
            }
        }
    }
}
