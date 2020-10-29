import java.util.*;

public class Movie{
    private String title;
    private Set<String> tags;

    public Movie(String title) {
        this(title, new String[0]);
    }
    public Movie(String title, String[] tags) {
        this.title = title;
        this.tags = new HashSet<>(Arrays.asList(tags));
    }

    @Override
    public String toString() {
        return title;
    }

    public Set<String> getTags() {
        return tags;
    }

    public String getTitle() {
        return title;
    }
}
