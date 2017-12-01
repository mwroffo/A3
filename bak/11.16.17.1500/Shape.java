import java.awt.Color;
import java.awt.Graphics;

public abstract class Shape {
    private int x;
    private int y;

    public Shape() {
        // empty constructor specifies default values:
        this.x = 2;
        this.y = 1;
    }
    public Shape(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // set and get methods:
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }

    // abstract methods to be overridden later:
    public abstract String getName();
    public abstract double getArea();
    public abstract double getPerimeter();

    @Override
    public String toString() {
        return String.format("created on %s");
    }

}