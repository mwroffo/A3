import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends GeometricObject {
    // fields:
    private double width;
    private double height;

    @Override
    public void draw() {
        System.out.println("test");
    }

    // constructors:
    public Rectangle() {
        super();
        // default values derived from test output:
        width = 10.0;
        height = 5.0;
    }

    public Rectangle(double width, double height) {
        super();
        this.width = width;
        this.height = height;
    }

    // set and get:
    public double getW() {
        return width;
    }

    public double getH() {
        return height;
    }

    public void setWidth(double width) {
        width = width;
    }

    public void setHeight(double height) {
        height = height;
    }

    // overrides:
    @Override
    public double getArea() {
        return getH() * getW();
    }
    @Override
    public double getPerimeter() {
        return 2 * getH() + 2 * getW();
    }

    // string formatting:
    @Override
    public String getName() {
        return String.format("[Rectangle] width = %.1f and height = %.1f",
            getW(), getH());
    }
    @Override
    public String toString() {
        return String.format("created on %s\ncolor: %s and filled: %s\n\n",
            getDateCreated(), getColor(), String.valueOf(isFilled()));
    }

    // returns the greatest rectangle, but based on what instance variable?
    // the spec does not specify...
    // in the absence of specific instructions, I've used area:
    @Override
    public int compareTo(GeometricObject that) {
        if (this.getArea() > that.getArea())
            return 1;
        if (this.getArea() == that.getArea())
            return 0;
        else
            return -1;
    }

    // compiler needs this overridden but it isn't needed:
    @Override
    public double getRadius() {
        return 0.0;
    }

}