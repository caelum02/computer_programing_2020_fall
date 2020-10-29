package evolution.of.truth.agent;

import evolution.of.truth.match.Match;
import kiroong.Individual;

public class Copykitten extends Agent{
    public Copykitten() {
        super("Copykitten");
    }

    @Override
    public Individual clone() {
        return new Copykitten();
    }

    private int opponentChoiceHistory;

    @Override
    public int choice(int previousOpponentChoice) {
        int _choice;

        if(previousOpponentChoice == Match.UNDEFINED)
            _choice = Match.COOPERATE;
        else if(opponentChoiceHistory == Match.CHEAT && previousOpponentChoice == Match.CHEAT)
            _choice = Match.CHEAT;
        else
            _choice = previousOpponentChoice;

        opponentChoiceHistory = previousOpponentChoice;
        return _choice;
    }
}
