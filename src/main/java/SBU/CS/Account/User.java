package SBU.CS.Account;

import SBU.CS.Database;
import SBU.CS.Notification;
import SBU.CS.Subreddit.Comment;
import SBU.CS.Subreddit.Post;
import SBU.CS.Subreddit.Subreddit;
import SBU.CS.Tools;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

import static java.lang.Thread.sleep;

public class User extends Account {
    private ArrayList<Post> posts = new ArrayList<>(); // posts published by user
    private ArrayList<Comment> comments = new ArrayList<>(); // replies written by user
    private ArrayList<Post> upVotedPosts = new ArrayList<>(); // posts the user has upvoted
    private ArrayList<Comment> upVotedComments = new ArrayList<>(); // comments the user has upvoted
    private ArrayList<Subreddit> joinedSubreddits = new ArrayList<>(); // user's subreddits
    private ArrayList<Notification> notifications = new ArrayList<>(); // user's notification inbox
    private ArrayList<Post> savedPosts = new ArrayList<>();

    public User(String username, String password, String firstName, String lastName, LocalDate birthday, String email) {
        super(username, password, firstName, lastName, birthday, email);
    }

    public int getTotalKarma() { // calculate the user's total karma
        int karma = getPostKarma() + getCommentKarma();

        return karma;
    }

    public int getPostKarma() { // calculate the post karma
        int karma = 0;
        for (Post post : posts) {
            karma += post.getKarma();
        }


        return karma;
    }

    public int getCommentKarma() { // calculate the comment karma
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

    public void displayMyProfile() throws InterruptedException { // display user's profile panel
        int i;
        boolean flag = true;
        while (flag) {
            Tools.clearScreen();
            System.out.println(Tools.BLUE_COLOR + "~~| My Profile |~~" + Tools.RESET_COLOR);
            System.out.println("### " + getUsername());
            System.out.printf("u/%s - %d karma - %s %d, %d\n", getUsername(), getTotalKarma(), getTimeCreated().getMonth(), getTimeCreated().getDayOfMonth(), getTimeCreated().getYear());

            System.out.println("""
                                        
                    0. Exit
                    1. Edit Profile
                    2. My Posts
                    3. My Comments
                    4. About
                    """);
            int command = Tools.handleErrors("an option", 0, 4);
            switch (command) {
                case 0:
                    flag = false;
                    break;
                case 1:
                    editProfile();
                    break;
                case 2:
                    i = 0;
                    System.out.println("0. Exit");
                    for (Post post : posts) {
                        i++;
                        System.out.println(i + ". ");
                        post.displayBrief();
                    }
                    command = Tools.handleErrors("an option", 0, posts.size());
                    if (command != 0)
                        posts.get(command - 1).viewUserActions(this);
                    break;
                case 3:
                    i = 0;
                    System.out.println("0. Exit");
                    for (Comment comment : comments) {
                        i++;
                        System.out.println(i + ". ");
                        comment.displayBrief();
                    }
                    command = Tools.handleErrors("an option", 0, posts.size());
                    if (command != 0)
                        comments.get(command - 1).viewUserActions(this);
                    break;
                case 4:
                    System.out.printf("Post karma: %d | Comment karma: %d \n", getPostKarma(), getCommentKarma());
                    break;
            }
            sleep(200);
        }
    }

    private void editProfile() throws InterruptedException { // allow user to edit different parts of their profile
        boolean flag = true;
        Scanner scanner = new Scanner(System.in);
        while (flag) {
            Tools.clearScreen();
            System.out.println(Tools.BLUE_COLOR + "~~| Edit Profile |~~" + Tools.RESET_COLOR);
            System.out.println("Username: " + getUsername());
            System.out.println("Email: " + getEmail());
            System.out.println("First Name: " + getFirstName());
            System.out.println("Last Name: " + getLastName());
            System.out.printf("Birthday: %d/%d/%d - Age: ", getBirthday().getYear(), getBirthday().getMonthValue(), getBirthday().getDayOfMonth(), getAge());

            System.out.println("""

                    0. Exit
                    1. Change Username
                    2. Change email
                    3. Change Password
                    4. Change First Name
                    5. Change Last Name
                    6. change Birthday""");
            int command = Tools.handleErrors("an option", 0, 6);
            String input;
            scanner.nextLine();
            String regex = "^[a-zA-Z]{2,}$";
            switch (command) {
                case 0:
                    flag = false;
                    break;
                case 1:
                    System.out.print("New Username: ");
                    input = scanner.nextLine();
                    if (Tools.stringIsValid(input))
                        changeUsername(input);
                    else
                        System.out.println("\u001B[31m Invalid input \u001B[0m: Entered string should be at least 8 characters and only contain alphabets, numbers and underscores");
                    break;
                case 2:
                    System.out.print("New Password: ");
                    input = scanner.nextLine();
                    if (Tools.stringIsValid(input))
                        changePassword(input);
                    else
                        System.out.println("\u001B[31m Invalid input \u001B[0m: Entered string should be at least 8 characters and only contain alphabets, numbers and underscores");
                    break;
                case 3:
                    System.out.print("New Email: ");
                    input = scanner.nextLine();
                    if (Tools.validateEmailFormat(input))
                        changeEmail(input);
                    else
                        System.out.println("\u001B[31m Invalid input \u001B[0m: Entered email format is incorrect");
                    break;
                case 4:
                    System.out.print("New First Name: ");
                    input = scanner.nextLine();
                    if (Pattern.matches(regex, input))
                        changeFirstName(input);
                    else
                        System.out.print("\u001B[31m Invalid input \u001B[0m: Entered string should be at least 1 character and only contain alphabets");
                    break;
                case 5:
                    System.out.print("New Last Name: ");
                    input = scanner.nextLine();
                    if (Pattern.matches(regex, input))
                        changeLastName(input);
                    else
                        System.out.print("\u001B[31m Invalid input \u001B[0m: Entered string should be at least 1 character and only contain alphabets");
                    break;
                case 6:
                    changeBirthday(Tools.getBirthday());
                    break;
            }
            sleep(200);
        }
    }

    private void createPost() { // creates a post with the user as publisher
        Tools.clearScreen();
        System.out.println(Tools.BLUE_COLOR + "~~| Create Post |~~" + Tools.RESET_COLOR);
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        System.out.print("0. Cancel");
        for (Subreddit subreddit : joinedSubreddits) {
            i++;
            System.out.printf("\n%d. r/%s : %d members", i, subreddit.getTitle(), subreddit.getMembers().size());
        }
        if (joinedSubreddits.isEmpty()) {
            System.out.println("You haven't joined any subreddits, Please do so before trying to create a post!");
        }
        int number = Tools.handleErrors("a subreddit", 0, joinedSubreddits.size());
        if (number == 0) {
            return;
        }
        Subreddit subreddit = joinedSubreddits.get(number - 1);
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Text: ");
        String text = scanner.nextLine();

        ArrayList<String> tags = Post.inputTags();
        System.out.println("Confirm creation of post: " +
                "\nTitle: " + title +
                "\nText: " + text +
                "\nSubreddit: " + subreddit.getTitle() +
                "\n1. Yes" +
                "\n2. No");
        if (Tools.handleErrors("an option", 1, 2) == 1) {
            Post post = new Post(title, text, subreddit, this, tags);
            posts.addFirst(post);
            System.out.print("Post created successfully");
        }
    }

    private void createSubreddit() { // allows user to create a custom subreddit
        Tools.clearScreen();
        System.out.println(Tools.BLUE_COLOR + "~~| Create Subreddit |~~" + Tools.RESET_COLOR);
        Scanner scanner = new Scanner(System.in);
        String regex = "^[a-zA-Z]{2,}$";

        System.out.print("Title: ");
        String title = scanner.nextLine();
        if (!Pattern.matches(regex, title)) {
            System.out.println("\u001B[31m Invalid input \u001B[0m: subreddit title should only contain alphabets and be more than 2 letters long");
            return;
        }

        for (Subreddit subreddit : Database.subreddits) {
            if (Objects.equals(subreddit.getTitle(), title)) {
                System.out.print("Invalid title: There is already a subreddit with this name, please try again");
                return;
            }
        }
        System.out.print("Explanation: ");
        String explanation = scanner.nextLine();

        System.out.println("Confirm creation of subreddit: " +
                "\nTitle: " + title +
                "\nExplanation: " + explanation +
                "\n1. Yes" +
                "\n2. No");
        if (Tools.handleErrors("an option", 1, 2) == 1) {
            Subreddit subreddit = new Subreddit(title, explanation, this);
            joinedSubreddits.add(subreddit);
            System.out.print("Subreddit created successfully");

        }
    }

    private void displayTimeline() throws InterruptedException { // displays user's timeline from followed subreddits
        Tools.clearScreen();
        System.out.println(Tools.BLUE_COLOR + "~~| Timeline |~~" + Tools.RESET_COLOR);
        ArrayList<Post> timeline = new ArrayList<>();
        for (Subreddit subreddit : joinedSubreddits) {
            timeline.addAll(subreddit.getPosts());
        }

        timeline.sort(Comparator.comparing(Post::getTimePublished)); // sort timeline according to post times

        int i;
        while (true) {
            Tools.clearScreen();
            System.out.println("~~| Timeline |~~");
            i = 0;
            System.out.println("0. Exit");
            for (Post post : timeline) {
                i++;
                System.out.println(i + ". ");
                post.displayBrief();
            }
            int input = Tools.handleErrors("a post", 0, timeline.size());
            if (input == 0) {
                break;
            }
            timeline.get(input - 1).viewUserActions(this);
        }

    }

    private void displayJoinedSubreddits() throws InterruptedException {
        int i;
        while (true) {
            Tools.clearScreen();
            System.out.println(Tools.BLUE_COLOR + "~~| Communities |~~" + Tools.RESET_COLOR);
            i = 0;
            System.out.println("0. Exit");
            for (Subreddit subreddit : joinedSubreddits) {
                i++;
                System.out.println(i + ". " + (subreddit.getAdmins().contains(this) ? "(Admin)" : ""));
                subreddit.displayBrief();
            }
            int input = Tools.handleErrors("a subreddit", 0, joinedSubreddits.size());
            if (input == 0) {
                break;
            }
            joinedSubreddits.get(input - 1).viewUserActions(this);
        }
    }

    private void searchDatabase() {
        String searchPrompt;

        while (true) {
            Tools.clearScreen();
            System.out.println(Tools.BLUE_COLOR + "~~| Search |~~" + Tools.RESET_COLOR);

            searchPrompt = getSearchPrompt();
            if (searchPrompt.equals("exit"))
                return;
            else
                displaySearchResults(searchPrompt);
        }
    }

    private String getSearchPrompt() {
        String searchPrompt;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("(type \"exit\" to leave to your panel)");
            System.out.print("Search Prompt: ");
            searchPrompt = scanner.nextLine();

            String pattern = "^[a-zA-Z0-9_]+$";
            if (Pattern.matches(pattern, searchPrompt) || searchPrompt.equals("exit"))
                return searchPrompt;

            System.out.print("\u001B[31m Invalid input \u001B[0m: Entered string should have at least 1 characters and only contain alphabets, numbers, underscores and \"u/\" or \"r/\"");
        }
    }

    private void displaySearchResults(String prompt) {
        int i, command;
        ArrayList<Subreddit> subredditResults = new ArrayList<>();
        ArrayList<User> userResults = new ArrayList<>();
        // find search results
        if (prompt.contains("r/")) {
            prompt = prompt.replaceFirst("r/", "");
            for (Subreddit subreddit : Database.subreddits) {
                if (subreddit.getTitle().contains(prompt)) {
                    subredditResults.add(subreddit);
                }
            }
        } else if (prompt.contains("u/")) {
            prompt = prompt.replaceFirst("u/", "");
            for (User user : Database.users) {
                if (user.getUsername().contains(prompt)) {
                    userResults.add(user);
                }
            }
        } else {
            for (Subreddit subreddit : Database.subreddits) {
                if (subreddit.getTitle().contains(prompt)) {
                    subredditResults.add(subreddit);
                }
            }
            for (User user : Database.users) {
                if (user.getUsername().contains(prompt)) {
                    userResults.add(user);
                }
            }
        }

        while (true) {
            System.out.printf("Results for %s: \n", prompt);
            System.out.println("0. Exit");
            i = 0;
            System.out.println("Communities: ");
            for (Subreddit subreddit : subredditResults) {
                i++;
                System.out.println(i + ". ");
                subreddit.displayBrief();
            }
            System.out.println("Users: ");
            for (User user : userResults) {
                i++;
                System.out.println(i + ". u/" + user.getUsername());
                // ToDo needs change after addition of following users
            }

            command = Tools.handleErrors("an option", 0, subredditResults.size() + userResults.size());
            if (command == 0)
                break;
            if (command > subredditResults.size()) {
                System.out.println("Selecting users is currently unavailable :)");
            } else {
                subredditResults.get(command - 1).displayComplete();
            }
        }

    }

    private void displayInbox() {
        Tools.clearScreen();
        System.out.println(Tools.BLUE_COLOR + "~~| Inbox |~~" + Tools.RESET_COLOR);

        Scanner scanner = new Scanner(System.in);
        int i = 0;
        for (Notification notification : notifications) {
            i++;
            System.out.printf("%d. Title: %s , Details: %s\n", i, notification.getTitle(), notification.getText());
        }
        System.out.println("Type anything to exit");
        String input = scanner.nextLine();
    }

    public void displayUserPanel() throws InterruptedException { // display user's panel
        boolean flag = true;
        while (flag) {
            Tools.clearScreen();
            System.out.println("""
                    \u001B[34m~~| Your Panel |~~\u001B[0m
                                        
                    0. Logout
                    1. My Profile
                    2. Create a Post
                    3. Create a Subreddit
                    4. Timeline
                    5. Communities
                    6. Search
                    7. Inbox""");
            // ToDo messaging and save posts and following users
            // ToDO allow user to see upvoted posts or comments
            int command = Tools.handleErrors("an option", 0, 7);
            switch (command) {
                case 0:
                    flag = false;
                    break;
                case 1:
                    displayMyProfile();
                    break;
                case 2:
                    createPost();
                    break;
                case 3:
                    createSubreddit();
                    break;
                case 4:
                    displayTimeline();
                    break;
                case 5:
                    displayJoinedSubreddits();
                    break;
                case 6:
                    searchDatabase();
                    break;
                case 7:
                    displayInbox();
                    break;
            }
        }
    }
}
