
import java.util.*;

public class MovieApp {

    Map<String, Movie> movieObjMap = new HashMap<>();
    Map<String, User> userObjMap = new HashMap<>();
    Map<Movie, Map<User, Integer>> ratingMap = new HashMap<>();
    Map<User, Set<Movie>> searchHistory = new HashMap<>();

    public boolean addMovie(String title, String[] tags) {
        // TODO sub-problem 1
        if(title == null || title.equals("") || movieObjMap.containsKey(title))
            return false;


        Movie movie = tags == null ? new Movie(title) : new Movie(title, tags);
        movieObjMap.put(title, movie);
        ratingMap.put(movie, new HashMap<>());

        return true;
    }

    public boolean addUser(String name) {
        // TODO sub-problem 1
        if(name == null || name.equals("") || userObjMap.containsKey(name))
            return false;

        User user = new User(name);
        userObjMap.put(name, user);
        searchHistory.put(user, new HashSet<>());

        return true;
    }

    public Movie findMovie(String title) {
        // TODO sub-problem 1

        // null if map contains no mapping for the given title
        return movieObjMap.get(title);
    }

    public User findUser(String username) {
        // TODO sub-problem 1

        // null if map contains no mapping for the given username
        return userObjMap.get(username);
    }

    public List<Movie> findMoviesWithTags(String[] tags) {
        // TODO sub-problem 2
        List<Movie> foundMovies = new ArrayList<>();

        if(tags == null || tags.length == 0)
            return foundMovies;

        List<String> tagsList = Arrays.asList(tags);
        for(Movie movie: movieObjMap.values()) {
            if(movie.getTags().containsAll(tagsList)) {
                foundMovies.add(movie);
            }
        }

        return sortedMovies(foundMovies);
    }

    static List<Movie> sortedMovies(List<Movie> movies) {
        movies.sort(new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
        });

        return movies;
    }

    public boolean rateMovie(User user, String title, int rating) {
        // TODO sub-problem 3
        if(title == null || !movieObjMap.containsKey(title)
                || user == null || !userObjMap.containsKey(user.getUsername())
                || !(1 <= rating && rating <= 10))
            return false;

        Movie ratedMovie = movieObjMap.get(title);

        ratingMap.get(ratedMovie).put(user, rating);

        return true;
    }

    public int getUserRating(User user, String title) {
        // TODO sub-problem 3
        if(title == null || !movieObjMap.containsKey(title)
                || user == null || !userObjMap.containsKey(user.getUsername()))
            return -1;

        Movie ratedMovie = movieObjMap.get(title);
        Integer rating = ratingMap.get(ratedMovie).get(user);

        // if the user has not rated the movie, return 0;
        return rating == null ? 0 : rating;
    }

    public List<Movie> findUserMoviesWithTags(User user, String[] tags) {
        // TODO sub-problem 4

        if(user == null || !userObjMap.containsKey(user.getUsername()))
            return new ArrayList<>();

        List<Movie> movies = findMoviesWithTags(tags);

        searchHistory.get(user).addAll(movies);

        return movies;
    }

    public List<Movie> recommend(User user) {
        // TODO sub-problem 4
        if(user == null || !userObjMap.containsKey(user.getUsername()))
            return new ArrayList<>();

        List<Movie> candidates = new ArrayList<>(searchHistory.get(user));

        candidates.sort(new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                int cmp = getAverageRating(o2).compareTo(getAverageRating(o1));
                return cmp == 0 ? o1.getTitle().compareTo(o2.getTitle()) : cmp;
            }
        });

        return candidates.size() < 3 ? candidates : candidates.subList(0, 3);
    }

    public Double getAverageRating(Movie movie) {

        int ratingSum = 0;

        if(movie == null || !movieObjMap.containsKey(movie.getTitle()))
            return 0.;

        Map<User, Integer> ratings = ratingMap.get(movie);

       for(Integer rating : ratings.values())
            ratingSum += rating;

       return ratings.isEmpty() ? 0 : (double) ratingSum / ratings.size();
    }
}
