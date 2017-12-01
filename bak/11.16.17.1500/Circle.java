import java.awt.Color;
import java.awt.Graphics;

public class Circle extends GeometricObject {
    // fields:
    private double radius;

    @Override
    public void draw() {
        System.out.println("test");
    }

    // constructors:
    public Circle() {
        super();
    }
    public Circle(double radius) {
        super();
        this.radius = radius;
    }
    public Circle(double radius, String color, boolean filled) {
        super();
        this.radius = radius;
        setColor(color);
        setFilled(filled);        
    }

    // set and get methods
    @Override
    public double getRadius() {
        return radius;
    }
    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getDiameter() {
        return getRadius() * 2.0;
    }

    // not necessary with radius spoken for
    // public void setDiameter(double diameter) {
    //     this.diameter = diameter;
    // }

    // returns area and parameter:
    @Override
    public double getArea() {
        return Math.PI * getRadius() * getRadius();
    }
    @Override
    public double getPerimeter() {
        return 2 * Math.PI * getRadius();
    }

    // string formatting:
    @Override
    public String getName() {
        return String.format("[Circle] radius = %.1f",
            getRadius());     
    }

    @Override
    public String toString() {
        return String.format("created on %s\ncolor: %s and filled: %s\n\n",
            getDateCreated(), getColor(), String.valueOf(isFilled()));
    }

    // specify that Circle objects should be compared by their radius:
    @Override
    public int compareTo(GeometricObject that) {
        if (this.getRadius() > that.getRadius())
            return 1;
        if (this.getRadius() == that.getRadius())
            return 0;
        else
            return -1;
    }
}