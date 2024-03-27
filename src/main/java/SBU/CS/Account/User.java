package SBU.CS.Account;

import SBU.CS.Subreddit.Post;

import java.util.ArrayList;

public class User extends Account {
    ArrayList<Post> posts = new ArrayList<>();

    public User(String username, String password, String firstName, String lastName, Birthday birthday) {
        super(username, password, firstName, lastName, birthday);
    }

    public void getKarma() {
        int karma = 0;
        for (Post post :posts) {

        }
    }
}
