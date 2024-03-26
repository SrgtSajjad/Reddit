package SBU.CS.Subreddit;
import java.time.LocalDateTime;
public class Post {
    String title;
    String text;
    Subreddit subreddit;
    LocalDateTime timeReleased;

    public Post(String title, String text, Subreddit subreddit) {
        this.title = title;
        this.text = text;
        this.subreddit = subreddit;
        timeReleased = LocalDateTime.now();
    }


}
