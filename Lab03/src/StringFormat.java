public class StringFormat {
    public static String writeInfo(String name, int age, float height) {
        return String.format("id: %s, age: %d, name: %f", name, age, height);
    }
    public static void main(String[] args) {
//        System.out.println("id " + 4 + " , age: " + 24);
//        System.out.println("id " + 5 + " , age: " + 23);
//        System.out.println("id " + 2 + " , age: " + 21);

        System.out.printf("id: %d, age: %d, name: %s", 4, 24, "Jack");
        System.out.println(writeInfo("Jack", 24, 179f));

    }
}
