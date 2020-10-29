import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BackEnd extends ServerResourceAccessible {
    // Use getServerStorageDir() as a default directory
    // TODO sub-program 1 ~ 4 :
    // Create helper functions to support FrontEnd class
    private final String serverStorageDir;
    private int maxId = -1;

    private Map<String, List<Post>> postsMap = new HashMap<>();
    private Map<String, List<String>> friendMap = new HashMap<>();

    BackEnd() {
        serverStorageDir = getServerStorageDir();
        loadData();
    }

    public String getUserPassword(String id) {
        List<String> lines = readFile(getFile(id + "/password.txt"));

        if(lines.size() != 1)
            return null;

        return lines.get(0);
    }

    public List<Post> recommend(User user) {
        List<String> friends = getFriends(user);

        List<Post> posts = new ArrayList<>();
        for(String friendId : friends) {
            List<Post> friendPosts = getPosts(friendId);
            posts.addAll(friendPosts);
        }

        posts.sort((Post o1, Post o2) -> {
            LocalDateTime dateTime1 = getDateTime(o1),
                    dateTime2 = getDateTime(o2);
            return dateTime2.compareTo(dateTime1);
        });

        return posts.subList(0, 10);
    }

    public List<String> readFile(File file) {
        if(file == null)
            return null;

        List<String> lines = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                lines.add(scanner.nextLine());
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            return null;
        }

        return lines;
    }

    public File getFile(String relativePath) {
        return new File(serverStorageDir + relativePath);
    }

    public void savePost(User user, String title, String content) {
        ArrayList<String> strings = new ArrayList<>();

        LocalDateTime dateTime = LocalDateTime.now();

        strings.add(dateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        strings.add(title);
        strings.add("");
        strings.add(content);

        int id = ++maxId;

        String path = serverStorageDir + String.format("%s/post/%d.txt", user.id, id);
        writeFile(path, strings);

        if(!postsMap.containsKey(user.id))
            postsMap.put(user.id, new ArrayList<>());

        postsMap.get(user.id).add(new Post(id, dateTime, title, content));
    }

    public void writeFile(String path, List<String> strings) {
        try {
            FileWriter fileWriter = new FileWriter(path);

            for (String line : strings) {
                fileWriter.write(line + "\n");
            }

            fileWriter.close();
        } catch (IOException ignored) {

        }
    }

    public File[] listFiles(String path) {
        return (new File(path)).listFiles();
    }

    public void loadData() {
        File[] userDirs = listFiles(serverStorageDir);
        for(File dir : userDirs) {
            String userName = dir.getName();

            List<String> friends = readFile(new File(dir.getPath() + "/friend.txt"));
            friendMap.put(userName, friends);

            File[] postFiles = (new File(dir.getPath() + "/post/")).listFiles();
            List<Post> posts = new ArrayList<>();

            if(postFiles != null) {
                for(File post : postFiles) {
                    List<String> lines = readFile(post);
                    String dateTimeString = lines.get(0);

                    int id = parsePostId(post);

                    // update Id
                    id = Math.max(id, maxId);

                    LocalDateTime dateTime = Post.parseDateTimeString(dateTimeString, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
                    String title = lines.get(1);
                    String content = String.join("\n", lines.subList(3, lines.size()));

                    posts.add(new Post(id, dateTime, title, content));
                }
            }

            postsMap.put(userName, posts);
        }
    }

    public static int parsePostId(File file) {
        return Integer.parseInt(file.getName().split("\\.")[0]);
    }

    public List<String> getFriends(User user) {
        if(!friendMap.containsKey(user.id))
            return null;

        return new ArrayList<>(friendMap.get(user.id));
    }

    public List<Post> getPosts(String userId) {
        if(!postsMap.containsKey(userId))
            return null;

        return postsMap.get(userId);
    }

    public List<Post> searchKeywords(List<String> keywords) {
        List<Pair<Post, Integer>> result = new ArrayList<>();

        for(List<Post> posts : postsMap.values()) {
            for(Post post : posts) {
                int occur = keywordOccurrences(keywords, post);

                if(occur > 0)
                    result.add(new Pair<>(post, occur));
            }
        }

        result.sort((o1, o2) -> {
            Integer occur1 = o1.value, occur2 = o2.value;

            return occur1.equals(occur2) ? getDateTime(o2.key).compareTo(getDateTime(o1.key))
                    : occur2.compareTo(occur1);
        });

        result = result.subList(0, Math.min(10, result.size()));

        List<Post> ret = new ArrayList<>(); for(Pair<Post, Integer> pair : result) ret.add(pair.key);

        return ret;
    }

    public int keywordOccurrences(List<String> keywords, Post post) {
        String toSearch = post.getTitle() + " " + post.getContent();

        int cnt = 0;
        for(String word : toSearch.split("[^a-zA-Z0-9]"))
            if(keywords.contains(word))
                cnt++;

        return cnt;
    }

    public LocalDateTime getDateTime(Post post) {
        return Post.parseDateTimeString(post.getDate(), DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }

}
