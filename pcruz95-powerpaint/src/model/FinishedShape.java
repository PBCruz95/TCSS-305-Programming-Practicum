/*
 * TCSS 305 Assignment 5 - PowerPaint
 */

package model;

import java.awt.Color;
import java.awt.Shape;

/**
 * This class represents a finished DrawShape. After the mouse is released, an
 * instance of FinishedShape is created and copies the color, thickness, and
 * shape used in the current shape, and this FinishedShape added to a List that
 * will keep reiterating to keep drawn shapes on the JPanel.
 * 
 * @author pcruz95
 * @version 1
 */
public class FinishedShape {

    /**
     * The shape's shape.
     */
    protected Shape myShape;

    /**
     * The shape's color.
     */
    private final Color myColor;

    /**
     * The shape's thickness.
     */
    private final int myThickness;

    /**
     * The constructor for the FinishedShape class.
     * 
     * @param theCopy the DrawShape instance whose fields will be copied into
     *            this instance of FinishedShape (it should be myCurrentShape).
     */
    public FinishedShape(final DrawShape theCopy) {
        myColor = theCopy.getColor();
        myThickness = theCopy.getThickness();
        myShape = theCopy.getShape();
    }

    /**
     * Returns this FinishedShape's color.
     * 
     * @return the shape's color
     */
    public Color getColor() {
        return myColor;
    }

    /**
     * Returns this FinishedShape's thickness.
     * 
     * @return the shape's thickness
     */
    public int getThickness() {
        return myThickness;
    }

    /**
     * Returns this FinishedShape's shape.
     * 
     * @return the shape's shape
     */
    public Shape getShape() {
        return myShape;
    }
}
