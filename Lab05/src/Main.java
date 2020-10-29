import evolution.of.truth.environment.Tournament;
import evolution.of.truth.match.Match;

public class Main {
    public static void main(String[] args) {
        Tournament tournament = new Tournament();
        for(int i=0; i<100; i++) {
            tournament.resetAgents();
            tournament.playAllGames(10);
            tournament.describe();
            tournament.evolvePopulation();
        }
    }
}
