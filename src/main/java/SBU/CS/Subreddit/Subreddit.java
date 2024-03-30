package SBU.CS.Subreddit;

import SBU.CS.Account.User;
import SBU.CS.Database;

import java.util.ArrayList;

public class Subreddit {
    private String title;
    private User creator;
    private ArrayList<User> admins = new ArrayList<>();
    private ArrayList<Post> posts = new ArrayList<>();
    private ArrayList<User> members = new ArrayList<>();
    private ArrayList<User> bannedUsers = new ArrayList<>();
    private String explanation;

    public Subreddit(String title, String explanation , User creator) {
        this.title = title;
        this.explanation = explanation;
        this.creator = creator;
        admins.add(creator); // automatically add the creator to the subreddit's admin list
        members.add(creator);
        Database.subreddits.add(this); // add the created subreddit to the database
    }

    public void displayBrief() {
        // ToDo
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
