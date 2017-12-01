import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Graphics;

public class Square extends GeometricObject {
    // fields:
    private double side;

    @Override
    public void draw() {
        System.out.println("test");
    }

    // constructors:
    public Square() {
        super();
        // default values derived from test output:
        this.side = 1.0;
    }

    public Square(double side) {
        super();
        this.side = side;
    }

    // set and get:
    public double getSide() {
        return side;
    }

    public void setSide(double side) {
        this.side = side;
    }

    // overrides:
    @Override
    public double getArea() {
        return getSide() * getSide();
    }
    @Override
    public double getPerimeter() {
        return 4 * getSide();
    }

    // string formatting:
    @Override
    public String getName() {
        return String.format("[Square] side = %.1f",
            getSide());
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