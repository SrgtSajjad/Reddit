package SBU.CS.Subreddit;

import SBU.CS.Account.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Comment {
    String text;
    User publisher;
    LocalDateTime timePublished;
    ArrayList<Comment> comments = new ArrayList<>();
    ArrayList<User> upVoters = new ArrayList<>();
    ArrayList<User> downVoters = new ArrayList<>();

    public Comment(String text, User publisher) {
        this.text = text;
        this.publisher = publisher;
        timePublished = LocalDateTime.now(); // for sorting user's timeline based on time

    }

    public int getKarma() {
        return upVoters.size() - downVoters.size();
    }

}
