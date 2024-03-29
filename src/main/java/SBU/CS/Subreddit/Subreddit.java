package SBU.CS.Subreddit;

import SBU.CS.Account.User;
import SBU.CS.Database;

import java.util.ArrayList;

public class Subreddit {
    String title;
    User creator;
    ArrayList<User> admins = new ArrayList<>();
    ArrayList<Post> posts = new ArrayList<>();
    ArrayList<User> members = new ArrayList<>();
    ArrayList<User> bannedUsers = new ArrayList<>();

    public Subreddit(String title, User creator) {
        this.title = title;
        this.creator = creator;
        admins.add(creator); // automatically add the creator to the subreddit's admin list
        Database.subreddits.add(this); // add the created subreddit to the database
        members.add(creator);
    }
}
