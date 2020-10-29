public class HelloClass {
    public static void main(String[] args) {
        Car car = new Car(100, "Sonata");
        car.printInfo();
        car.verboseInfo();
    }
}

class Car{
    int carNumber;
    String model;

    // Constructor
    Car(int carNumber, String model) {
        this.carNumber = carNumber;
        this.model = model;
    }

    void printInfo() {
        System.out.printf("carNumber: %d, model: %s\n", carNumber, model);
    }

    void verboseInfo() {
        System.out.println("Car information");
        printInfo();
        System.out.println();
    }
}
