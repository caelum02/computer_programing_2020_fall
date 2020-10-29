import java.util.Scanner;

public class CharacterCounter {
    public static void countCharacter(String str) {
        int[] cnt = new int[26];
        for(int i=0; i<str.length(); i++){
            cnt[str.charAt(i)-'a']++;
        }

        for(int i=0; i<26; i++) {
            if(cnt[i]>0)
                printCount((char)((int)('a')+i), cnt[i]);
        }
    }

    private static void printCount(char character, int count) {
        System.out.printf("%c: %d times\n", character, count);
    }
}