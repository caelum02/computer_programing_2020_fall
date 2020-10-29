import java.util.*;

public class practice1 {
    public static void main(String[] args){
        int n;
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt(); sc.nextLine();

        String[] s = new String[n];
        for(int i=0; i<n; i++){
            s[i] = sc.nextLine(); // sc.next()도 가능. 구분자는 입력 받지 않
            System.out.println(i+" is "+s[i]);
        }

        for(int i=0; i<n; i++) System.out.println(s[i]);
        for(int i=n-1; i>=0; i--) System.out.println(s[i]);
    }
}
