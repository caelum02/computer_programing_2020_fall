package hand.market;

import hand.agent.Buyer;
import hand.agent.Seller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

class Pair<K,V> {
    public K key;
    public V value;
    Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

public class Market {
    public ArrayList<Buyer> buyers;
    public ArrayList<Seller> sellers;

    public Market(int nb, ArrayList<Double> fb, int ns, ArrayList<Double> fs) {
        buyers = createBuyers(nb, fb);
        sellers = createSellers(ns, fs);
    }
    
    private ArrayList<Buyer> createBuyers(int n, ArrayList<Double> f) {
        // TODO sub-problem 3
        ArrayList<Buyer> buyers = new ArrayList<Buyer>();
        for(int i=1; i<=n; i++){
            double priceLimit = 0, x = (double) i / n;
            for(int j=0; j<f.size(); j++)
                priceLimit += f.get(j) * Math.pow(x, f.size()-1-j);

            buyers.add(new Buyer(priceLimit));
        }

        return buyers;
    }

    private ArrayList<Seller> createSellers(int n, ArrayList<Double> f) {
        // TODO sub-problem 3
        ArrayList<Seller> sellers = new ArrayList<Seller>();
        for(int i=1; i<=n; i++){
            double priceLimit = 0, x = (double) i / n;
            for(int j=0; j<f.size(); j++)
                priceLimit += f.get(j) * Math.pow(x, f.size()-1-j);

            sellers.add(new Seller(priceLimit));
        }

        return sellers;
    }

    private ArrayList<Pair<Seller, Buyer>> matchedPairs(int day, int round) {
        ArrayList<Seller> shuffledSellers = new ArrayList<>(sellers);
        ArrayList<Buyer> shuffledBuyers = new ArrayList<>(buyers);
        Collections.shuffle(shuffledSellers, new Random(71 * day + 43 * round + 7));
        Collections.shuffle(shuffledBuyers, new Random(67 * day + 29 * round + 11));
        ArrayList<Pair<Seller, Buyer>> pairs = new ArrayList<>();
        for (int i = 0; i < shuffledBuyers.size(); i++) {
            if (i < shuffledSellers.size()) {
                pairs.add(new Pair<>(shuffledSellers.get(i), shuffledBuyers.get(i)));
            }
        }
        return pairs;
    }

    public double simulate() {
        // TODO sub-problem 2 and 3
        int countTransaction = 0; double priceAverage = 0;
        for (int day = 1; day <= 1000; day++) { // do not change this line
            for (int round = 1; round <= 10; round++) { // do not change this line
                ArrayList<Pair<Seller, Buyer>> pairs = matchedPairs(day, round); // do not change this line
                for(Pair<Seller, Buyer> pair : pairs){
                    Seller seller = pair.key; Buyer buyer = pair.value;
                    double price = seller.getExpectedPrice();
                    if(buyer.willTransact(price) && seller.willTransact(price)){
                        seller.makeTransaction();
                        buyer.makeTransaction();
                        if(day == 1000)
                            priceAverage += (seller.getExpectedPrice() - priceAverage) / (double)(++countTransaction);

                    }
                }
            }
            for(Buyer buyer : buyers)
                buyer.reflect();
            for(Seller seller : sellers)
                seller.reflect();
        }

        return priceAverage;
    }
}
