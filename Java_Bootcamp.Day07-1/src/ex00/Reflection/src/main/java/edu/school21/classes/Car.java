package edu.school21.classes;

import java.util.StringJoiner;

public class Car {
    private String name;
    private double speed;
    private boolean isNew;
    private int countOwner;

    public Car() {
        this.name = "Default car name";
        this.speed = 100;
        this.isNew = true;
        this.countOwner = 0;
    }

    public Car (String name, double speed, boolean isNew, int countOwner) {
        this.name = name;
        this.speed = speed;
        this.isNew = isNew;
        this.countOwner = countOwner;
    }

    public boolean Buy() {
        if (isNew) {
            isNew = false;
            ++countOwner;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("speed=" + speed)
                .add("isNew=" + isNew)
                .add("countOwner=" + countOwner)
                .toString();
    }
}
