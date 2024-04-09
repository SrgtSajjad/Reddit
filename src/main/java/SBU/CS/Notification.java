package SBU.CS;

import java.io.Serializable;

public class Notification implements Serializable {

    private String title;
    private String text;


    public Notification(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
