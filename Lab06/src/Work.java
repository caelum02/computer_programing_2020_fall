public class Work {
    private static int numWorks = 0;
    private int id;

    Work() {
        id = numWorks++;
    }

    public int getId(){
        return id;
    }
}
