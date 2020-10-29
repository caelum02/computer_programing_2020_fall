import java.util.Scanner;

public class IncreasingString {
    public static void printLongestIncreasingSubstringLength(String inputString) {
        int max=1, cnt=1;
        for(int i=0; i<inputString.length()-1; i++) {
            if(inputString.charAt(i+1)-'a' > inputString.charAt(i)-'a')
                cnt++;
            else {
                max = Math.max(max, cnt);
                cnt = 1;
            }
        }
        max = Math.max(max, cnt);

        System.out.println(max);
    }
}
