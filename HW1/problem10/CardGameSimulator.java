public class CardGameSimulator {
    private static final Player[] players = new Player[2];

    public static void simulateCardGame(String inputA, String inputB) {
        players[0] = new Player("A", strCards(inputA.split(" ")));
        players[1] = new Player("B", strCards(inputB.split(" ")));

        int turn = 0;
        Card previousCard = new Card(players[turn].maxNum(), 'O');

        for(int i=0; i<20; i++) {
            turn = i%2;
            int idx = players[turn].query(previousCard);
            if (idx==-1) {
                printLoseMessage(players[turn]);
                return;
            }
            players[turn].useCard(idx);
            previousCard = players[turn].getCard(idx);
        }
        printLoseMessage(players[0]);
    }

    private static Card[] strCards(String[] cards){
        Card[] ret = new Card[cards.length];
        for(int i=0; i<cards.length; i++)
            ret[i] = new Card(cards[i]);

        return ret;
    }

    private static void printLoseMessage(Player player) {
        System.out.printf("Player %s loses the game!\n", player);
    }
}


class Player {
    private String name;
    private Card[] deck;

    public void playCard(Card card) {
        System.out.printf("Player %s: %s\n", name, card);
    }

    public int query(Card previousCard) {
        int number = previousCard.getNumber();
        int idxO=-1, idxX=-1;

        for(int i=0; i<deck.length; i++)
            if((!deck[i].used) && number==deck[i].getNumber()) {
                if(deck[i].getShape()=='O') idxO=i;
                else if(deck[i].getShape()=='X') idxX=i;
            }

        return idxO >= 0 ? idxO : (idxX >= 0 ? idxX : maxNumOfShapeIdx(previousCard.getShape()));
    }

    public int maxNumOfShapeIdx (char shape) {
        int maxNumber = -1, maxIdx = -1;

        for(int i=0; i<deck.length; i++)
            if((!deck[i].used) && (shape==deck[i].getShape()) && deck[i].getNumber() > maxNumber) {
                maxIdx = i; maxNumber = deck[i].getNumber();
            }

        return maxIdx;
    }

    public int maxNum() {
        int maxNumber = -1;
        for (Card card : deck) {
            maxNumber = Math.max(maxNumber, card.getNumber());
        }
        return maxNumber;
    }

    @Override
    public String toString() {
        return name;
    }

    Player (String name, Card[] deck) {
        this.name = name;
        this.deck = deck;
    }

    public Card getCard(int idx) {
        return deck[idx];
    }

    public void useCard(int idx) {
        deck[idx].used = true;
        playCard(deck[idx]);
    }
}


class Card {
    private int number;
    private char shape;
    boolean used = false;

    @Override
    public String toString() {
        return "" + number + shape;
    }

    Card (String str) {
        this(str.charAt(0)-'0', str.charAt(1));
    }

    Card (int number, char shape) {
        this.number = number;
        this.shape = shape;
    }

    public int getNumber(){
        return number;
    }
    public char getShape() {
        return shape;
    }
}
