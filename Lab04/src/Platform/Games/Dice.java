package Platform.Games;

public class Dice {
    public int playGame() {
        int user = rollDice(), opponent = rollDice();
        System.out.println(user + " " + opponent);

        if(user > opponent) return 1;
        else if(user < opponent) return -1;
        else return 0;
    }
    private static int rollDice() {
        return (int) (Math.random() * 100);
    }
}
