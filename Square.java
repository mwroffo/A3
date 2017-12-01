import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Graphics;

public class Square extends GeometricObject {
    // fields:
    private double side;

    @Override
    public void draw(Graphics g) {
        if (!isFilled())
            g.drawRect(50, 50, (int)this.getSide(), (int)this.getSide());
        else
            g.fillRect(50, 50, (int)this.getSide(), (int)this.getSide());
    }

    // constructors:
    public Square() {
        super();
        // default values derived from test output:
        this.side = 1.0;
    }

    public Square(int xPos, int yPos,
        double side, Color c, boolean filled) {
        super();
        this.side = side;
        setColor(c);
        setFilled(filled);
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
        return String.format("color: %s and filled: %s\n\n",
            getColor(), String.valueOf(isFilled()));
    }

    // compiler needs this overridden but it isn't needed:
    @Override
    public double getRadius() {
        return 0.0;
    }

}