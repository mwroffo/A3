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
import java.awt.event.MouseAdapter;
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

    // ButtonGroups organize the menu item:
    private ButtonGroup fontButtonGroup;
    private ButtonGroup colorButtonGroup;
    private ButtonGroup shapeButtonGroup;
    private ButtonGroup backgroundButtonGroup;
    private ButtonGroup styleButtonGroup;

    private static JComboBox shapeComboBox;
    private static JCheckBox fillCheckBox;
    private static boolean filled = false;

    private static JTextArea resultTextArea;

    private static BorderLayout layout;

    private Circle circle;
    private Rectangle rectangle;
    private Square square;

    private DrawShapePanel drawShapePanel;

    private static int xPos, yPos, r, w, h, s;

    private static Color color = Color.BLACK;

    // arraylist to store points when drawing with mouse:
    private final ArrayList<Point> points = new ArrayList<>();

    // CONSTRUCTOR:
    public ShapeDesignerFrame() {
        // assemble layout:
        super("Drawing Shapes and Displaying all Info");
        layout = new BorderLayout();
        setLayout(layout);
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

        // add the mouse listener:
        addMouseListener(new MouseClickHandler());
        addMouseMotionListener(
            new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent event) {
                    String s = String.format("%d, %d", event.getX(), event.getY());
                    resultTextArea.setText(s);
                }
            }
        );

        // intialize results area:
        resultTextArea = new JTextArea(20, 50);

        // init graphics context:
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

        // build buttons:
        changeColorButton = new JButton("Change Color");
        changeColorButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    setColorFromChooser();
                    repaint();
                }
            }
        );
    } // end method buildAllElements

    /**
    * addAllElements then adds all of the GUI components to the frame:
    */
    private void addAllElements() {
        add(drawShapePanel, BorderLayout.NORTH);
        add(resultTextArea, BorderLayout.CENTER);
        add(shapeComboBox, BorderLayout.SOUTH);
        add(changeColorButton, BorderLayout.SOUTH);
        add(fillCheckBox, BorderLayout.SOUTH);

        // set Circle as default upon opening application:
        showRadiusOnly();
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
            // create shape in order to
            circle = new Circle(xPos, yPos, r, color, filled);
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
            rectangle = new Rectangle(xPos, yPos, w, h, color, filled);
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
            square = new Square(xPos, yPos, s, color, filled);
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
    public static int getXPos() {
        return xPos;
    }
    public static int getYPos() {
        return yPos;
    }
    public static int getDist(int x, int y, int x0, int y0) {
        return (int)Math.sqrt((x - x0) * (x - x0) - (y - y0) * (y - y0));
    }

    // ****************
    // HANDLER CLASSES:
    // ****************

    private class MouseClickHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            xPos = event.getX();
            yPos = event.getY();
        }
        public void mouseReleased(MouseEvent event) {
            if (getSelectedComboBoxItem().equals("Circle")) {
                r = getDist(xPos, yPos, event.getX(), event.getY());
                setCircle();
            }
            if (getSelectedComboBoxItem().equals("Rectangle")) {
                w = Math.abs(event.getX() - xPos);
                h = Math.abs(event.getY() - yPos);
                setRectangle();
            }
            if (getSelectedComboBoxItem().equals("Square")) {
                int yDist = Math.abs(event.getY() - yPos);
                int xDist = Math.abs(event.getX() - xPos);
                s = (yDist >= xDist) ? yDist : xDist; // use whichever d is greater
                setSquare();
            }
            repaint();
        }
    }

    // handles changes in fonts:
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
                    resultTextArea.setFont(f);
                }
            }
            repaint();
        }
    }

    // HANDLER FOR THE SHAPE COMBO BOX:
    private class ComboBoxHandler implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent event) {
            if (getSelectedComboBoxItem().equals("Circle")) {
                setCircle();
            }
            if (getSelectedComboBoxItem().equals("Rectangle")) {
                setRectangle();
            }
            if (getSelectedComboBoxItem().equals("Square")) {
                setSquare();
            }
            repaint();
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