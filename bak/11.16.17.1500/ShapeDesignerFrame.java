// Program: DesignShapes.java
// Written by: Michael Roffo
// Description: A GUI that generates shapes.
// Challenges: Learning new Layout managers.
// Time Spent: 8 hours
// 
//                   Revision History
// Date:                   By:               Action:
// ---------------------------------------------------
// 10/31/17 (MR) created.
// 11/2/17 (MR) completed major functionalities.
// 11/3/17 (MR) polished GUI design.
// 
// 11/10/17 (MR) modified and extended for DrawShapesGUI.
// 11/12/17 (MR) completed GUI layout, developed 
// graphics context called DrawShapePanel and added
// appropriate repaint() calls in the setCircle(), setRectangle(), 
// and setSquare() methods.

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Component;
import java.awt.event.ActionListener; // to handle events
import java.awt.event.ActionEvent; // to create event objects
import java.awt.event.ItemListener; // for combo box
import java.awt.event.ItemEvent;
import javax.swing.JFrame; // for the entire frame library, etc.
import javax.swing.JLabel; // needs labels,
import javax.swing.JTextField; // textfields,
import javax.swing.JComboBox; // for selecting shape
import javax.swing.JButton; // and buttons.
import javax.swing.JOptionPane; // JOptionPane for alerts.
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class ShapeDesignerFrame extends JFrame {
    private JTextField radiusTextField;
    private JTextField widthTextField;
    private JTextField heightTextField;
    private JTextField sideTextField;

    private static JComboBox shapeComboBox;

    private JLabel shapeIsResultLabel;
    private JLabel areaIsResultLabel;
    private JLabel perimeterIsResultLabel;

    private GridBagLayout layout;
    private GridBagConstraints constraints;

    private Circle circle;
    private Rectangle rectangle;
    private Square square;

    private DrawShapePanel drawShapePanel;

    private static double r;
    private static double w;
    private static double h;
    private static double s;

    // CONSTRUCTOR:
    public ShapeDesignerFrame() {
        // assemble layout:
        super();
        layout = new GridBagLayout();
        setLayout(layout);
        constraints = new GridBagConstraints();

        // BUILD GRAPHICS CONTEXT:
        drawShapePanel = new DrawShapePanel();

        // build shapeComboBox:
        String[] shapes = {"Circle", "Rectangle", "Square"};
        shapeComboBox = new JComboBox<String>(shapes);
        // register listeners for Combo box elements:
        ComboBoxHandler comboBoxHandler = new ComboBoxHandler();
        shapeComboBox.addItemListener(comboBoxHandler);

        // build textfields:
        radiusTextField = new JTextField(10);
        widthTextField = new JTextField(5);
        heightTextField = new JTextField(5);
        sideTextField = new JTextField(10);

        // build labels: 
        JLabel chooseShapeLabel = new JLabel("Choose a shape:");
        JLabel inputDataLabel = new JLabel("INPUT DATA:");
        JLabel radiusLabel = new JLabel("Radius:");
        JLabel widthLabel = new JLabel("Width:");
        JLabel heightLabel = new JLabel("Height:");
        JLabel sideLabel = new JLabel("Side:");
        JLabel resultLabel = new JLabel("RESULT:");
        JLabel shapeIsLabel = new JLabel("Shape is:");
        JLabel areaIsLabel = new JLabel("Area is:");
        JLabel perimeterIsLabel = new JLabel("Perimeter is:");
        shapeIsResultLabel = new JLabel("");
        areaIsResultLabel = new JLabel("");
        perimeterIsResultLabel = new JLabel("");

        // build buttons:
        JButton getButton = new JButton("Draw Shape");
        JButton clearButton = new JButton("Clear");
        JButton exitButton = new JButton("Exit");
        // initialize button handlers:
        GetButtonHandler getButtonHandler = new GetButtonHandler();
        ClearButtonHandler clearButtonHandler = new ClearButtonHandler();
        ExitButtonHandler exitButtonHandler = new ExitButtonHandler();
        // register button handlers:
        getButton.addActionListener(getButtonHandler);
        clearButton.addActionListener(clearButtonHandler);
        exitButton.addActionListener(exitButtonHandler);

        // add all components to frame: 
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.BOTH;
        addComponent(chooseShapeLabel, 0, 0, 1, 1);
        addComponent(shapeComboBox, 0, 1, 1, 1);
        addComponent(inputDataLabel, 1, 0, 1, 1);
        addComponent(radiusLabel, 3, 0, 1, 1);
        addComponent(radiusTextField, 3, 1, 1, 1);
        addComponent(widthLabel, 4, 0, 1, 1);
        addComponent(widthTextField, 4, 1, 1, 1);
        addComponent(heightLabel, 5, 0, 1, 1);
        addComponent(heightTextField, 5, 1, 1, 1);
        addComponent(sideLabel, 6, 0, 1, 1);
        addComponent(sideTextField, 6, 1, 1, 1);
        addComponent(resultLabel, 7, 0, 1, 1);
        addComponent(shapeIsLabel, 8, 0, 1, 1);
        addComponent(shapeIsResultLabel, 8, 1, 1, 1);
        addComponent(areaIsLabel, 9, 0, 1, 1);
        addComponent(areaIsResultLabel, 9, 1, 1, 1);
        addComponent(perimeterIsLabel, 10, 0, 1, 1);
        addComponent(perimeterIsResultLabel, 10, 1, 1, 1);
        addComponent(getButton, 11, 0, 1, 1);
        addComponent(clearButton, 11, 1, 1, 1);
        addComponent(exitButton, 11, 2, 1, 1);

        // add the graphics context panel:
        
        constraints.weighty = 1;
        addComponent(drawShapePanel, 20, 0, 3, 1);


        // set up for default Circle:
        showRadiusOnly();

    }// end GUI constructor

    // THESE METHODS SET TEXT BOXES EDITABLE AND NONEDITABLE AS APPROPRIATE:
    public void showRadiusOnly() {
        clearTextFields();
        radiusTextField.setEditable(true);
        widthTextField.setEditable(false);
        heightTextField.setEditable(false);
        sideTextField.setEditable(false);
    }
    public void showHeightAndWidthOnly() {
        clearTextFields();
        widthTextField.setEditable(true);
        heightTextField.setEditable(true);
        radiusTextField.setEditable(false);
        sideTextField.setEditable(false);
    }
    public void showSideOnly() {
        clearTextFields();
        sideTextField.setEditable(true);
        radiusTextField.setEditable(false);
        widthTextField.setEditable(false);
        heightTextField.setEditable(false);
    }
    public void clearTextFields() {
        radiusTextField.setText("");
        heightTextField.setText("");
        widthTextField.setText("");
        sideTextField.setText("");
    }
    public void clearResultFields() {
        shapeIsResultLabel.setText("");
        areaIsResultLabel.setText("");
        perimeterIsResultLabel.setText("");
    }
    public void clearGraphicsContext() {
        w = 0.0;
        h = 0.0;
        s = 0.0;
        r = 0.0;
        repaint();
    }

    // I'm not positive this a good design, making shapeComboBox static?
    // returns the String inside of ComboBox:
    public static String getSelectedComboBoxItem() {
        return (String)shapeComboBox.getSelectedItem();
    }

    // THESE METHODS CREATE SHAPES, DRAW THE SHAPE, AND HANDLE EXCEPTIONS: 
    public void setCircle() throws NumberFormatException {
        try {
            r = Double.parseDouble(radiusTextField.getText());
            // create shape:
            circle = new Circle(r);
            // display its dimensions:
            shapeIsResultLabel.setText(circle.getName());
            areaIsResultLabel.setText(
                String.format("%.2f", circle.getArea()));
            perimeterIsResultLabel.setText(
                String.format("%.2f", circle.getPerimeter()));
            // draw it:
            drawShapePanel.repaint();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error. Radius must be numeric.");
        }
    }
    public void setRectangle() throws NumberFormatException {
        try {
            w = Double.parseDouble(widthTextField.getText());
            h = Double.parseDouble(heightTextField.getText());
            rectangle = new Rectangle(w, h);
            shapeIsResultLabel.setText(rectangle.getName());
            areaIsResultLabel.setText(
                String.format("%.2f", rectangle.getArea()));
            perimeterIsResultLabel.setText(
                String.format("%.2f", rectangle.getPerimeter()));
            drawShapePanel.repaint();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error. Height and width must be numeric.");
        }
    }
    public void setSquare() throws NumberFormatException {
        try {
            s = Double.parseDouble(sideTextField.getText());
            square = new Square(s);
            shapeIsResultLabel.setText(square.getName());
            areaIsResultLabel.setText(
                String.format("%.2f", square.getArea()));
            perimeterIsResultLabel.setText(
                String.format("%.2f", square.getPerimeter()));
            drawShapePanel.repaint();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error. Side must be numeric.");
        }
    }

    // HANDLER FOR THE SHAPE COMBO BOX:
    private class ComboBoxHandler implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent event) {
            JComboBox cb = (JComboBox)event.getSource();
            if (cb.getSelectedItem().equals("Circle"))
                showRadiusOnly();
            if (cb.getSelectedItem().equals("Rectangle"))
                showHeightAndWidthOnly();
            if (cb.getSelectedItem().equals("Square"))
                showSideOnly();
        }          
    }

    // HANDLER FOR THE GET BUTTON:
    private class GetButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if (getSelectedComboBoxItem().equals("Circle"))
                setCircle();
            if (getSelectedComboBoxItem().equals("Rectangle"))
                setRectangle();
            if (getSelectedComboBoxItem().equals("Square"))
                setSquare();
        }
    }

    // HANDLER FOR THE CLEAR BUTTON:
    private class ClearButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            clearTextFields();
            clearResultFields();
            clearGraphicsContext();
        }
    }

    // HANDLER FOR THE EXIT BUTTON:
    private class ExitButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            System.exit(0);
        }
    }

    // GET METHODS FOR THE DIMENSIONS:
    public static double getR() {
        return r;
    }
    public static double getWei() {
        return w;
    }
    public static double getHei() {
        return h;
    }
    public static double getS() {
        return s;
    }

    // essential private helper function for handling constraints
    // when adding each component to frame with GrabBagLayout:
    private void addComponent(Component component, 
        int row, int column, int width, int height) {
        constraints.gridx = column;
        constraints.gridy = row;
        constraints.gridwidth = width;
        constraints.gridheight = height;
        layout.setConstraints(component, constraints);
        add(component);
    }

    private class DrawShapePanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            this.setBackground(Color.LIGHT_GRAY);
            
            g.setColor(Color.BLACK);
            if (ShapeDesignerFrame.getSelectedComboBoxItem().equals("Circle"))
                g.drawOval(150, 200, (int)ShapeDesignerFrame.getR(), 
                    (int)ShapeDesignerFrame.getR());
            if (ShapeDesignerFrame.getSelectedComboBoxItem().equals("Rectangle"))
                g.drawRect(150, 200, (int)ShapeDesignerFrame.getWei(), 
                    (int)ShapeDesignerFrame.getHei());
            if (ShapeDesignerFrame.getSelectedComboBoxItem().equals("Square"))
                g.drawRect(150, 200, (int)ShapeDesignerFrame.getS(), 
                    (int)ShapeDesignerFrame.getS());
        }
    }

    // TEST CLIENT:
    public static void main(String[] args) {
        ShapeDesignerFrame shapeDesignerFrame = new ShapeDesignerFrame();
        shapeDesignerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        shapeDesignerFrame.setSize(700, 700);
        shapeDesignerFrame.setVisible(true);
    }
}// end class ShapeDesignerPanel