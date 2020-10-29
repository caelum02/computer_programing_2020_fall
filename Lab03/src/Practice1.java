import java.util.Scanner;

public class Practice1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        do {
            for(int i=0; i<N; i++){
                for(int j=0; j<N; j++)
                    System.out.print(i==j || i+j==N-1 ? "X" : "O");
                System.out.println();
            }
            N = sc.nextInt();
        }
        while(N > 0);
    }
}
