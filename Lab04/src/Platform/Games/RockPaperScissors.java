package Platform.Games;

import java.util.Scanner;

public class RockPaperScissors {
    private static final String[] intToRsp = {"scissor", "rock", "paper"};

    public int playGame() {
        Scanner scanner = new Scanner(System.in);
        int user = rspToInt(scanner.nextLine());

        if(user==-1) return -1;

        int opponent = choose();
        System.out.println(intToRsp[user]+" "+ intToRsp[opponent]);

        return (user-opponent+1)%3-1;
    }

    private static int choose() {
        return (int)(3 * Math.random());
    }

    private static int rspToInt(String rsp) {
        for(int i=0; i<3; i++) {
            if(rsp.equals(intToRsp[i])) {
                return i;
            }
        }

        return -1;
    }
}
