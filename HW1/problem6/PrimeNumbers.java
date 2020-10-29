import java.util.Scanner;

public class PrimeNumbers {
    public static void printPrimeNumbers(int n) {
        int i=2;
        while(n>0) {
            int cnt = 0;
            boolean isPrime = true;

            for(int j=2; j<i; j++){
                if(i%j==0) {
                    isPrime = false; break;
                }
            }

            if(isPrime){
                System.out.print(i + " ");
                n--;
            }
            i++;
        }
    }
}
