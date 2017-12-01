// Program: GeometricObject.java
// Written by: Michael Roffo
// Description: A class defining an object for representing any GeometricObject.
// Challenges: using setX and setY rather than this.x = x.
// Time Spent: 2 hours
// 
//                   Revision History
// Date:                   By:               Action:
// ---------------------------------------------------
// 9/20/17 (MR) unsure whether to use Date or Calendar?
// 10/3/17 (MR) decided on Date.

import java.util.Date;

public abstract class GeometricObject extends Shape implements Comparable<GeometricObject> {
    // fields:
    private String color;
    private boolean filled;
    private Date dateCreated;

    // constructors:
    public GeometricObject() {
        super();
        this.color = "white";
        this.filled = false;
        dateCreated = new Date();
    }

    public GeometricObject(String color, boolean filled) {
        super();
        this.color = color;
        this.filled = filled;
        dateCreated = new Date();
    }

    public GeometricObject(int x, int y, String color, boolean filled) {
        super(x, y);
        this.color = color;
        this.filled = filled;
        setX(x);
        setY(y);
        dateCreated = new Date();
    }

    // set and get:
    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isFilled() {
        return this.filled;
    }
    public void setFilled(boolean isFilled) {
        this.filled = isFilled;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public abstract void draw();

    // toString() states date of creation:
    @Override
    public String toString() {
        return String.format("created on %s");
    }

    @Override
    public abstract int compareTo(GeometricObject o);

    public static GeometricObject max(GeometricObject o1, GeometricObject o2) {
        if (o1.compareTo(o2) > 0)
            return o1;
        else
            return o2;
    }

    // abstracts and overrides:
    @Override
    public abstract String getName();
    @Override
    public abstract double getArea();
    @Override
    public abstract double getPerimeter();
    public abstract double getRadius();
}