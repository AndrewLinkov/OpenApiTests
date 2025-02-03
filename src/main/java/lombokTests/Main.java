package lombokTests;

public class Main {
    public static void main(String[] args) {
        //Car car1 = new Car("BMW", 2020, "red", 200);

        Car car2 = new Car();
        car2.setSpeed(240);

        System.out.println("Result: " + car2);
    }
}