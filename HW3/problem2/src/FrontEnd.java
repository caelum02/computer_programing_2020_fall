import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDateTime;

public class FrontEnd {
    private UserInterface ui;
    private BackEnd backend;
    private User user;

    public FrontEnd(UserInterface ui, BackEnd backend) {
        this.ui = ui;
        this.backend = backend;
    }

    public boolean auth(String authInfo){
        // TODO sub-problem 1
        String[] parsedAuthInfo = parseAuthInfo(authInfo);

        if(parsedAuthInfo == null){
            return false;
        }

        String id = parsedAuthInfo[0], passwordIn = parsedAuthInfo[1];
        String password = backend.getUserPassword(id);

        if(password.equals(passwordIn)){
            user = new User(id, password);
            return true;
        }

        return false;
    }
    public void post(Pair<String, String> titleContentPair) {
        // TODO sub-problem 2
        String title = titleContentPair.key, content = titleContentPair.value;
        backend.savePost(user, title, content);
    }
    
    public void recommend(){
        // TODO sub-problem 3
        List<Post> posts = backend.recommend(user);

        for(Post post : posts)
            ui.println(post.toString());
    }

    public void search(String command) {
        // TODO sub-problem 4
        List<String> keywords = parseSearchKeywords(command);
        List<Post> posts = backend.searchKeywords(keywords);

        for(Post post : posts)
            ui.println(post.getSummary());
    }
    
    User getUser(){
        return user;
    }

    private String[] parseAuthInfo(String authInfo) {
        String[] splitAuthInfo = authInfo.split("\\n");

        if(splitAuthInfo.length != 2)
            return null;

        return splitAuthInfo;
    }

    private List<String> parseSearchKeywords(String command) {
        String[] split = command.split(" ");
        return new ArrayList<>(new HashSet<>(Arrays.asList(split).subList(1, split.length)));
    }
}
