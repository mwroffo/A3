import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends GeometricObject {
    // fields:
    private double width, height;

    @Override
    public void draw(Graphics g) {
        if (!isFilled())
            g.drawRect(50, 50, (int)this.getW(), (int)this.getH());
        else
            g.fillRect(50, 50, (int)this.getW(), (int)this.getH());
    }

    // constructors:
    public Rectangle() {
        super();
        // default values derived from test output:
        width = 10.0;
        height = 5.0;
    }

    public Rectangle(int xPos, int yPos,
        double width, double height,
        Color color, boolean filled) {
        super();
        this.width = width;
        this.height = height;
        setColor(color);
        setFilled(filled);
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
        return String.format("color: %s and filled: %s\n\n",
            getColor(), String.valueOf(isFilled()));
    }

    // compiler needs this overridden but it isn't needed:
    @Override
    public double getRadius() {
        return 0.0;
    }
}