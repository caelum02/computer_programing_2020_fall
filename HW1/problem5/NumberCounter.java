public class NumberCounter {
    public static void countNumbers(String str0, String str1, String str2) {
        int a = Integer.parseInt(str0),
                b = Integer.parseInt(str1),
                c = Integer.parseInt(str2);
        a *= b*c;

        int[] digitCnt = new int[10];
        while(a>0) {
            digitCnt[a%10]++; a/=10;
        }

        for(int i=0; i<10; i++){
            if(digitCnt[i] > 0)
                printNumberCount(i, digitCnt[i]);
        }
    }

    private static void printNumberCount(int number, int count) {
        System.out.printf("%d: %d times\n", number, count);
    }
}
