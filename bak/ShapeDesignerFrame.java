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
// 11/16/17 (MR) moved graphics context from another file
// into a private class in this same file.
// runtime errors when using shape objects
// local to the paintComponent method...
// works fine with sloppy static methods. argh.

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Component;
import java.awt.event.ActionListener; // to handle events
import java.awt.event.ActionEvent; // to create event objects
import java.awt.event.ItemListener; // for combo box
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JFrame; // for the entire frame library, etc.
import javax.swing.JLabel; // needs labels,
import javax.swing.JTextField; // textfields,
import javax.swing.JComboBox; // for selecting shape
import javax.swing.JButton; // and buttons.
import javax.swing.JOptionPane; // JOptionPane for alerts.
import java.awt.Color;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
// for menu bars:
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.ButtonGroup;
import javax.swing.JTextArea;
import java.awt.Font;

public class ShapeDesignerFrame extends JFrame {
    // menus:
    // radios to pick one:
    private JRadioButtonMenuItem[] colorItems; // black, blue, red, green
    private JRadioButtonMenuItem[] fontItems;  // serif, sans-serif, mono
    // checkboxes to pick multiple:
    private JCheckBoxMenuItem[] styleItems;    // italic, bold, both.
    private JRadioButtonMenuItem[] shapeItems;
    private JRadioButtonMenuItem[] backgroundItems;
    private int style = 0; // assists with style

    // these are the buttons we will need:
    private static JButton changeColorButton;
    private static JButton getButton;
    private static JButton clearButton;
    private static JButton exitButton;

    // ButtonGroups organize the menu item:
    private ButtonGroup fontButtonGroup;
    private ButtonGroup colorButtonGroup;
    private ButtonGroup shapeButtonGroup;
    private ButtonGroup backgroundButtonGroup;
    private ButtonGroup styleButtonGroup;

    private static JTextField radiusTextField;
    private static JTextField widthTextField;
    private static JTextField heightTextField;
    private static JTextField sideTextField;

    private static JComboBox shapeComboBox;
    private static JCheckBox fillCheckBox;
    private static boolean filled = false;

    private static JLabel shapeIsResultLabel;
    private static JLabel areaIsResultLabel;
    private static JLabel perimeterIsResultLabel;
    private static JLabel chooseShapeLabel;
    private static JLabel inputDataLabel;
    private static JLabel radiusLabel;
    private static JLabel widthLabel;
    private static JLabel heightLabel;
    private static JLabel sideLabel;
    private static JLabel resultLabel;
    private static JLabel shapeIsLabel;
    private static JLabel areaIsLabel;
    private static JLabel perimeterIsLabel;

    private static JTextArea resultTextArea;

    private static GridBagLayout layout;
    private static GridBagConstraints constraints;

    private Circle circle;
    private Rectangle rectangle;
    private Square square;

    private DrawShapePanel drawShapePanel;

    private static double r;
    private static double w;
    private static double h;
    private static double s;

    private static Color color = Color.BLACK;

    // arraylist to store points when drawing with mouse:
    private final ArrayList<Point> points = new ArrayList<>();

    // CONSTRUCTOR:
    public ShapeDesignerFrame() {
        // assemble layout:
        super("Drawing Shapes and Displaying all Info");
        layout = new GridBagLayout();
        setLayout(layout);
        constraints = new GridBagConstraints();
        buildAllElements();
        addAllElements();
    }// end GUI constructor

    /**
    * buildComponents builds all GUI objects that will go in the frame:
    */
    private void buildAllElements() {
        // intialize drawMenu to hold the Shape menu:
        JMenu drawMenu = new JMenu("Draw");
        drawMenu.setMnemonic('D');
        // shapeMenu contains Circle, Rect, Square options:
        String[] shapes = {"Circle", "Rectangle", "Square"};
        JMenu shapeMenu = new JMenu("Shape");
        shapeMenu.setMnemonic('S');
        // initialize JRadioButtonMenuItems, to be declared in a for-loop...
        shapeItems = new JRadioButtonMenuItem[shapes.length];
        shapeButtonGroup = new ButtonGroup();
        // create JRadioButtonMenuItems for the entire list:
        for (int i = 0; i < shapes.length; i++) {
            shapeItems[i] = new JRadioButtonMenuItem(shapes[i]);
            shapeMenu.add(shapeItems[i]); // add that radio button to the shape menu
            shapeButtonGroup.add(shapeItems[i]); // add that radio button to the shape button group
            shapeItems[i].addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        for (int j = 0; j <= shapeItems.length; j++) {
                            if (event.getSource() == shapeItems[j]) {
                                ShapeDesignerFrame.getShapeComboBox().setSelectedIndex(j);
                            }
                        }
                    }
                }
            );
        }
        shapeItems[0].setSelected(true); // set the default.
        drawMenu.add(shapeMenu);

        // create menubar and add drawMenu to it:
        JMenuBar bar = new JMenuBar();
        setJMenuBar(bar);
        bar.add(drawMenu);

        // create main menu textMenu:
        JMenu textMenu = new JMenu("Text");
        textMenu.setMnemonic('T');

        FontHandler fontHandler = new FontHandler();

        // create sub menu FONT FAMILY:
        String[] fontNames = {"Serif", "Monospaced", "SansSerif"};
        JMenu fontMenu = new JMenu("Font");
        fontMenu.setMnemonic('F');
        fontItems = new JRadioButtonMenuItem[fontNames.length];
        fontButtonGroup = new ButtonGroup();
        for (int i = 0; i < fontNames.length; i++) {
            fontItems[i] = new JRadioButtonMenuItem(fontNames[i]);
            fontMenu.add(fontItems[i]);
            fontButtonGroup.add(fontItems[i]);
            fontItems[i].addActionListener(fontHandler);
        }
        fontMenu.addSeparator();

        // add sub menu FONT STYLES MENU:
        String[] styleNames = {"Bold", "Italic"};
        styleItems = new JCheckBoxMenuItem[styleNames.length];
        styleButtonGroup = new ButtonGroup();
        for (int i = 0; i < styleNames.length; i++) {
            styleItems[i] = new JCheckBoxMenuItem(styleNames[i]);
            fontMenu.add(styleItems[i]);
            styleButtonGroup.add(styleItems[i]);
            styleItems[i].addActionListener(fontHandler);
        }
        
        textMenu.add(fontMenu);
        textMenu.addSeparator();

        // create sub menu FONT COLORS:
        String[] colors = {"Black", "Blue", "Red", "Green"};
        Color[] colorObjects = {Color.BLACK, Color.BLUE, Color.RED, Color.GREEN};
        JMenu colorMenu = new JMenu("Color");
        colorMenu.setMnemonic('C');
        colorItems = new JRadioButtonMenuItem[colors.length];
        colorButtonGroup = new ButtonGroup();
        for (int i = 0; i < colors.length; i++) {
            colorItems[i] = new JRadioButtonMenuItem(colors[i]);
            colorMenu.add(colorItems[i]);
            colorButtonGroup.add(colorItems[i]);
            colorItems[i].addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        for (int j = 0; j <= colorObjects.length; j++) {
                            if (event.getSource() == colorObjects[j]) {
                                ShapeDesignerFrame.setColor(colorObjects[j]);
                            }
                        }
                        repaint();
                    }
                }
            );
        }
        colorItems[0].setSelected(true);
        textMenu.add(colorMenu);
        textMenu.addSeparator();

        // create sub menu Background:
        String[] backgrounds = {"White", "Cyan", "Yellow", "Light_Gray"};
        JMenu backgroundMenu = new JMenu("Background");
        backgroundMenu.setMnemonic('B');
        backgroundItems = new JRadioButtonMenuItem[backgrounds.length];
        backgroundButtonGroup = new ButtonGroup();
        for (int i = 0; i < backgrounds.length; i++) {
            backgroundItems[i] = new JRadioButtonMenuItem(backgrounds[i]);
            backgroundMenu.add(backgroundItems[i]);
            backgroundButtonGroup.add(backgroundItems[i]);
            backgroundItems[i].addItemListener(
                new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        // set background color:
                    }
                }
            );
        }
        backgroundItems[0].setSelected(true);
        textMenu.add(backgroundMenu);

        // finally, add textMenu to the main bar. drawMenu is already in there...
        bar.add(textMenu);

        // BUILD GRAPHICS CONTEXT:
        drawShapePanel = new DrawShapePanel();

        // build fillCheckBox, unselected:
        fillCheckBox = new JCheckBox("Filled", false);
        fillCheckBox.addItemListener(
            new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent event) {
                    if (fillCheckBox.isSelected())
                        filled = true;
                    if (!fillCheckBox.isSelected())
                        filled = false;
                    repaint();
                }
            }
        );

        // build shapeComboBox:
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
        chooseShapeLabel = new JLabel("Choose a shape:");
        inputDataLabel = new JLabel("INPUT DATA:");
        radiusLabel = new JLabel("Radius:");
        widthLabel = new JLabel("Width:");
        heightLabel = new JLabel("Height:");
        sideLabel = new JLabel("Side:");
        resultLabel = new JLabel("RESULT:");
        shapeIsLabel = new JLabel("Shape is:");
        areaIsLabel = new JLabel("Area is:");
        perimeterIsLabel = new JLabel("Perimeter is:");
        shapeIsResultLabel = new JLabel("");
        areaIsResultLabel = new JLabel("");
        perimeterIsResultLabel = new JLabel("");

        // build buttons:
        getButton = new JButton("Draw Shape");
        clearButton = new JButton("Clear");
        exitButton = new JButton("Exit");
        changeColorButton = new JButton("Change Color");
        // initialize button handlers:
        GetButtonHandler getButtonHandler = new GetButtonHandler();
        ClearButtonHandler clearButtonHandler = new ClearButtonHandler();
        ExitButtonHandler exitButtonHandler = new ExitButtonHandler();
        // register button handlers:
        getButton.addActionListener(getButtonHandler);
        clearButton.addActionListener(clearButtonHandler);
        exitButton.addActionListener(exitButtonHandler);
        changeColorButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    setColorFromChooser();
                    repaint();
                }
            }
        );
    } // end method buildComponents

    /**
    * addAllElements then adds all of the GUI components to the frame:
    */
    private void addAllElements() {
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.BOTH;
        // input:
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
        addComponent(fillCheckBox, 8, 1, 1, 1);
        // output:
        addComponent(resultLabel, 10, 0, 1, 1);
        addComponent(shapeIsLabel, 11, 0, 1, 1);
        addComponent(shapeIsResultLabel, 11, 1, 1, 1);
        addComponent(areaIsLabel, 12, 0, 1, 1);
        addComponent(areaIsResultLabel, 12, 1, 1, 1);
        addComponent(perimeterIsLabel, 13, 0, 1, 1);
        addComponent(perimeterIsResultLabel, 13, 1, 1, 1);

        addComponent(getButton, 14, 0, 1, 1);
        addComponent(clearButton, 14, 1, 1, 1);
        addComponent(exitButton, 14, 2, 1, 1);
        addComponent(changeColorButton, 7, 1, 1, 1);

        // add the graphics context panel:
        constraints.weighty = 1;
        addComponent(drawShapePanel, 20, 0, 3, 1);

        // set Circle as default upon opening application:
        showRadiusOnly();
    }

    // handles GrabBagLayout constraints when adding each component to frame:
    private void addComponent(Component component, 
        int row, int column, int width, int height) {
        constraints.gridx = column;
        constraints.gridy = row;
        constraints.gridwidth = width;
        constraints.gridheight = height;
        layout.setConstraints(component, constraints);
        add(component);
    }

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
        // try g.clearRect(x, y, w, h);
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
    public static JComboBox getShapeComboBox() {
        return shapeComboBox;
    }

    // THESE METHODS CREATE SHAPES, DRAW THE SHAPE, AND HANDLE EXCEPTIONS: 
    public void setCircle() throws NumberFormatException {
        try {
            r = Double.parseDouble(radiusTextField.getText());
            // create shape in order to
            circle = new Circle(r, color, filled);
            // display its dimensions:
            shapeIsResultLabel.setText(circle.getName());
            areaIsResultLabel.setText(
                String.format("%.2f", circle.getArea()));
            perimeterIsResultLabel.setText(
                String.format("%.2f", circle.getPerimeter()));
            // draw it:
            repaint();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error. Radius must be numeric.");
        }
    }
    public void setRectangle() throws NumberFormatException {
        try {
            w = Double.parseDouble(widthTextField.getText());
            h = Double.parseDouble(heightTextField.getText());
            rectangle = new Rectangle(w, h, color, filled);
            shapeIsResultLabel.setText(rectangle.getName());
            areaIsResultLabel.setText(
                String.format("%.2f", rectangle.getArea()));
            perimeterIsResultLabel.setText(
                String.format("%.2f", rectangle.getPerimeter()));
            repaint();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error. Height and width must be numeric.");
        }
    }
    public void setSquare() throws NumberFormatException {
        try {
            s = Double.parseDouble(sideTextField.getText());
            square = new Square(s, color, filled);
            shapeIsResultLabel.setText(square.getName());
            areaIsResultLabel.setText(
                String.format("%.2f", square.getArea()));
            perimeterIsResultLabel.setText(
                String.format("%.2f", square.getPerimeter()));
            repaint();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error. Side must be numeric.");
        }
    }


    // get a color from JColorChooser:
    public void setColorFromChooser() {
        color = JColorChooser.showDialog(ShapeDesignerFrame.this, "Choose a color: ", color);            
        if (color == null)
            color = Color.BLACK;
    }
    // return the color:
    public static Color getColor() {
        return color;
    }
    public void resetColorToDefault() {
        color = Color.BLACK;
    }
    public static void setColor(Color c) {
        color = c;
    }
    // return the filled:
    public static boolean isFilled() {
        return filled;
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

    // ****************
    // HANDLER CLASSES:
    // ****************

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

    private class FontHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            // new String[] fontNames is only in scope for this class!
            String[] fontNames = {"Serif", "Monospaced", "SansSerif"};
            for (int i = 0; i < fontNames.length; i++) {
                // if the JRadioButtonMenuItem event that
                // triggered the event is the same object
                // as the one we are now iterating on,
                // then fontNames[i] will yield the
                // correct font family
                if (event.getSource() == fontItems[i] ||
                    event.getSource() == styleItems[i]) {
                    // note that fontNames[i] contains the name
                    // of the font family we want...

                    // then set font style based on what
                    // is selected inside the styleItems array:
                    if (styleItems[0].isSelected() && 
                          styleItems[1].isSelected())
                        style = 3;
                    else if (styleItems[0].isSelected())
                        style = 1;
                    else if (styleItems[1].isSelected())
                        style = 2;
                    else
                        style = 0;

                    // set all result fields to this:
                    Font f = new Font(fontNames[i], style, 12);
                    shapeIsResultLabel.setFont(f);
                    areaIsResultLabel.setFont(f);
                    perimeterIsResultLabel.setFont(f);
                }
            }
            repaint();
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

    // HANDLER FOR THE CLEAR BUTTON:
    private class ClearButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            clearTextFields();
            clearResultFields();
            clearGraphicsContext();
            resetColorToDefault();
        }
    }

    // HANDLER FOR THE EXIT BUTTON:
    private class ExitButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            System.exit(0);
        }
    }

    // TEST CLIENT:
    public static void main(String[] args) {
        ShapeDesignerFrame shapeDesignerFrame = new ShapeDesignerFrame();
        shapeDesignerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        shapeDesignerFrame.setSize(700, 700);
        shapeDesignerFrame.setVisible(true);
    }
}// end class ShapeDesignerFrame