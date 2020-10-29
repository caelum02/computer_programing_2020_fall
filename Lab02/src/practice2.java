import java.util.*;

public class practice2 {
    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        String id = scanner.nextLine();

        boolean flag = true;
        if(id.length() != 10 || id.charAt(4) != '-'){
            flag=false;
        }
        for(int i=0; flag && i<10; i++){
            if(i==4) continue;
            if(!Character.isDigit(id.charAt(i))){
                flag = false;
            }
        }
        System.out.println(id + " is" + (flag?"":" not") + " a valid Student ID");


    }
}
