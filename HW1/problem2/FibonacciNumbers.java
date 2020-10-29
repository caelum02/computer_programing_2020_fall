public class FibonacciNumbers {
    public static void printFibonacciNumbers(int n) {
        int a=0, b=1;

        System.out.printf("%d ", a);
        for(int i=1; i<n; i++){
            System.out.printf("%d ", b);
            int t=b;
            b = a+t;
            a = t;
        }
    }
}
