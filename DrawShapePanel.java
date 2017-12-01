import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JMenuItem;
import java.awt.Color;

public class DrawShapePanel extends JPanel {
    private GeometricObject shape;

    public DrawShapePanel() {
        shape = null;
    }

    public DrawShapePanel(GeometricObject s) {
        shape = s;
    }

    public void setShape() {
        if (ShapeDesignerFrame.getSelectedComboBoxItem().equals("Circle")) {
            Circle circle = new Circle(
                ShapeDesignerFrame.getXPos(),
                ShapeDesignerFrame.getYPos(),
                ShapeDesignerFrame.getR(),
                ShapeDesignerFrame.getColor(),
                ShapeDesignerFrame.isFilled());
            shape = (Circle)circle;
        }
        if (ShapeDesignerFrame.getSelectedComboBoxItem().equals("Rectangle")) {
            Rectangle rect = new Rectangle(
                ShapeDesignerFrame.getXPos(),
                ShapeDesignerFrame.getYPos(),
                ShapeDesignerFrame.getWei(),
                ShapeDesignerFrame.getHei(),
                ShapeDesignerFrame.getColor(),
                ShapeDesignerFrame.isFilled());
            shape = (Rectangle)rect;
        }
        if (ShapeDesignerFrame.getSelectedComboBoxItem().equals("Square")) {
            Square sq = new Square(
                ShapeDesignerFrame.getXPos(),
                ShapeDesignerFrame.getYPos(),
                ShapeDesignerFrame.getS(),
                ShapeDesignerFrame.getColor(),
                ShapeDesignerFrame.isFilled());
            shape = (Square)sq;
        }
        // repaint();
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
}