package SBU.CS.Subreddit;

import SBU.CS.Account.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

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
        timePublished = LocalDateTime.now(); // for sorting user's timeline based on time
        subreddit.posts.add(this); // adds the created post to a subreddit's  post list
    }


}
