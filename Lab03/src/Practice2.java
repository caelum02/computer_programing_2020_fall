import java.util.Scanner;

public class Practice2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        do {
            for (int i=2; i<N; i++) {
                boolean isPrime = true;
                for (int j=2; j<i; j++) {
                    if(i%j==0){
                        isPrime = false;
                        break;
                    }
                }
                if(isPrime) System.out.printf("%d ", i);
            }
            System.out.println();
            N = sc.nextInt();
        } while(N > 0);
    }
}
