/*
 * TCSS 305 Assignment 5 - PowerPaint
 */

package model;

import java.awt.Color;
import java.awt.Shape;

/**
 * An abstract class that implements the DrawShape interface.
 * 
 * @author pcruz95
 * @version 2
 */
public abstract class AbstractShape implements DrawShape {

    /**
     * The AbstractShape's current shape.
     */
    protected Shape myShape;

    /**
     * The AbstractShape's current color.
     */
    private Color myColor;

    /**
     * The AbstractShape's current thickness.
     */
    private int myThickness;

    /**
     * The constructor for the AbstractShape abstract class.
     * 
     * @param theColor this AbstractShape's color
     * @param theThickness this AbstractShape's thickness
     */
    public AbstractShape(final Color theColor, final int theThickness) {
        myColor = theColor;
        myThickness = theThickness;
    }

    /**
     * Sets this AbstractShape's color.
     * 
     * @param theColor the new color this AbstractShape will be set to
     */
    public void setColor(final Color theColor) {
        myColor = theColor;
    }

    /**
     * Sets this AbstractShape's thickness.
     * 
     * @param theThickness the new thickness this AbstractShape will be set to
     */
    public void setThickness(final int theThickness) {
        myThickness = theThickness;
    }

    /**
     * Returns this AbstractShape's color.
     * 
     * @return myColor
     */
    public Color getColor() {
        return myColor;
    }

    /**
     * Returns this AbstractShape's thickness.
     * 
     * @return myThickness
     */
    public int getThickness() {
        return myThickness;
    }

    /**
     * Get's the AbstractShape's current shape, which depends on which child
     * class instantiates it.
     * 
     * @return myShape
     */
    public abstract Shape getShape();
}
