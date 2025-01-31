package lombokTests;

import lombok.Data;

@Data
public class Car {
    private String model;
    private int year;
    private String color;
    private int speed;

    /*
    public Car(String model, int year, String color, int speed) {
        this.model = model;
        this.year = year;
        this.color = color;
        this.speed = speed;
    }

    public Car(String model) {
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", year=" + year +
                ", color='" + color + '\'' +
                ", speed=" + speed +
                '}';
    }

     */
}
