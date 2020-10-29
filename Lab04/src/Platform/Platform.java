package Platform;

import java.util.Scanner;
import Platform.Games.*;

public class Platform {
    private int rounds = 1;
    boolean isRoundsSet = false;

    public float run(){
        Scanner sc = new Scanner(System.in);
        int whichGame = sc.nextInt(), winCount = 0;

        switch(whichGame) {
            case 0:
                Dice dice = new Dice();
                for(int i=0; i<rounds; i++){
                    if(dice.playGame()==1) winCount++;
                }
                break;

            case 1:
                RockPaperScissors rsp = new RockPaperScissors();
                for(int i=0; i<rounds; i++){
                    if(rsp.playGame()==1) winCount++;
                }
                break;
        }
        return (float) winCount / rounds;
    }

    public void setRounds(int num) {
        if(isRoundsSet) return;
        rounds = num;
        isRoundsSet = true;
    }
}
