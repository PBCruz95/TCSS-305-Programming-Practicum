/*
 * TCSS 305 Assignment 5 - PowerPaint
 */

package model;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;

/**
 * An interface for possible shapes that can be drawn in this project.
 * 
 * @author pcruz95
 * @version 1
 */
public interface DrawShape {

    /**
     * Sets the shape's thickness to a new value.
     * 
     * @param theThickness the shape's new thickness
     */
    void setThickness(int theThickness);

    /**
     * Sets the shape's color to a new value.
     * 
     * @param theColor the shape's new color
     */
    void setColor(Color theColor);

    /**
     * Returns the shape's color.
     * 
     * @return the shape's color
     */
    Color getColor();

    /**
     * Returns the shape's thickness.
     * 
     * @return the shape's thickness
     */
    int getThickness();

    /**
     * The action to be performed when the mouse is pressed.
     * 
     * @param thePoint the point where the mouse is pressed
     * @return the shape
     */
    Shape press(Point thePoint);

    /**
     * The action to be performed when the mouse is dragged.
     * 
     * @param thePoint the point where the mouse is dragged to
     * @return the shape
     */
    Shape drag(Point thePoint);

    /**
     * The action to be performed when the mouse is released. This essentially
     * does what drag(Point) does, but I created this method for my own
     * clarity's sake.
     * 
     * @param thePoint the point where the mouse is released
     * @return the shape
     */
    Shape completeShape(Point thePoint);

    /**
     * Returns the shape's shape.
     * 
     * @return the shape's shape
     */
    Shape getShape();

}
