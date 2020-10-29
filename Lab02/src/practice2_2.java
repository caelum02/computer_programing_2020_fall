import java.util.Scanner;

public class practice2_2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String id = scanner.nextLine();
        validateStudentID(id);
    }
    static void validateStudentID(String id) {
        if (!isProperLength(id)){
            System.out.println("The input length should be 10.");
//            return false;
        }
        if (!hasProperDivision(id)) {
            System.out.println("Fifth character must be '-'.");
//            return false;
        }
        if (!hasProperDigits(id)) {
            System.out.println("Contains an invalid digit.");
//            return false;
        }

        System.out.println(id + " is a valid student ID");
//        return true;
    }
    static boolean isProperLength(String id) {
        return id.length() == 10;
    }
    static boolean hasProperDivision(String id) {
        return id.charAt(4) == '-';
    }
    static boolean hasProperDigits (String id) {
        for(int i=0; i<10; i++){
            if(i==4) continue;
            if(!Character.isDigit(id.charAt(i))) return false;
        }
        return true;
    }
}
