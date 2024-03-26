package SBU.CS.Subreddit;

import SBU.CS.Account.User;
import SBU.CS.Database;

import java.util.ArrayList;

public class Subreddit {
    String title;
    User creator;
    ArrayList<User> admins = new ArrayList<>();
    ArrayList<String> flairTags = new ArrayList<>();
    ArrayList<Post> posts = new ArrayList<>();

    public Subreddit(String title, User creator) {
        this.title = title;
        this.creator = creator;
        admins.add(creator);
        Database.subreddits.add(this);
    }
}
