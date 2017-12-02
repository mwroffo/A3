import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JMenuItem;
import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

public class DrawShapePanel extends JPanel {
    private static GeometricObject shape;
    private static int xPos0, yPos0, xPos, yPos;
    private static Color color;
    private static boolean filled;

    public DrawShapePanel() {
        addMouseListener(new MouseClickHandler());
        shape = null;
    }

    public DrawShapePanel(GeometricObject s) {
        addMouseListener(new MouseClickHandler());
        shape = s;
    }
    public static void setXPos0(int x) {
        xPos0 = x;
    }
    public static void setYPos0(int y) {
        yPos0 = y;
    }
    public static void setXPos(int x) {
        xPos = x;
    }
    public static void setYPos(int y) {
        yPos = y;
    }
    public static int getXPos0() {
        return xPos0;
    }
    public static int getYPos0() {
        return yPos0;
    }
    public static int getXPos() {
        return xPos;
    }
    public static int getYPos() {
        return yPos;
    }
    public static GeometricObject getShape() {
        return shape;
    }

    public void setShape() throws NumberFormatException {
        // System.out.printf("xPos0 = %d, yPos0 = %d\n",
        //     ShapeDesignerFrame.getXPos0(),
        //     ShapeDesignerFrame.getYPos0());
        if (ShapeDesignerFrame.getSelectedComboBoxItem().equals("Circle")) {
            try { 
                Circle circle = new Circle(
                    xPos0,
                    yPos0,
                    getDist(xPos, yPos, xPos0, yPos0),
                    ShapeDesignerFrame.getColor(),
                    ShapeDesignerFrame.isFilled()
                );
                shape = (Circle)circle;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error. Radius must be numeric.");
            }
        }
        if (ShapeDesignerFrame.getSelectedComboBoxItem().equals("Rectangle")) {
            try {
                Rectangle rect = new Rectangle(
                    xPos0,
                    yPos0,
                    (int)Math.abs(xPos - xPos0),
                    (int)Math.abs(yPos - yPos0),
                    ShapeDesignerFrame.getColor(),
                    ShapeDesignerFrame.isFilled());
                shape = (Rectangle)rect;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error. Height and width must be numeric.");
            }
        }
        if (ShapeDesignerFrame.getSelectedComboBoxItem().equals("Square")) {
            try {
                int yDist = (int)Math.abs(yPos0 - yPos);
                int xDist = (int)Math.abs(xPos0 - xPos);
                Square sq = new Square(
                    xPos0,
                    yPos0,
                    (yDist >= xDist) ? yDist : xDist,
                    ShapeDesignerFrame.getColor(),
                    ShapeDesignerFrame.isFilled());
                shape = (Square)sq;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error. Side must be numeric.");
            }
        }
    }

    private class MouseClickHandler extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent event) {
            setXPos0(event.getX());
            setYPos0(event.getY());
        }
        @Override
        public void mouseReleased(MouseEvent event) {
            setXPos(event.getX());
            setYPos(event.getY());
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.LIGHT_GRAY);
        
        // color set within the frame is the color for the graphics context:
        g.setColor(ShapeDesignerFrame.getColor());
        // set shape object to something constructed based on the combobox inputs:
        setShape();
        if (shape != null) {
            
            shape.draw(g);
        }
    }

    // gets pythagorian distance:
    private static int getDist(int x, int y, int x0, int y0) {
        return (int)Math.sqrt((x - x0) * (x - x0) + (y - y0) * (y - y0));
    }

}