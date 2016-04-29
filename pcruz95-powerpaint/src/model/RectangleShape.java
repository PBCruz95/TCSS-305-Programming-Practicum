/*
 * TCSS 305 Assignment 5 - PowerPaint
 */

package model;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Line2D;

/**
 * A child of the AbstractShape that returns a Rectangle2D.Double Shape based on the
 * given points.
 * 
 * @author pcruz95
 * @version 2
 */
public class RectangleShape extends AbstractShape {

    /**
     * The initial point when the mouse is first pressed.
     */
    private Point myInitialPoint;

    /**
     * The constructor for the RectangleShape class.
     * 
     * @param theColor the RectangleShape's color
     * @param theThickness the RectangleShape's thickness
     */
    public RectangleShape(final Color theColor, final int theThickness) {
        super(theColor, theThickness);
    }

    @Override
    public Shape press(final Point thePoint) {
        myInitialPoint = thePoint;
        myShape = new Line2D.Double(myInitialPoint, myInitialPoint).getBounds2D();
        return myShape;
    }

    @Override
    public Shape drag(final Point thePoint) {
        myShape = new Line2D.Double(myInitialPoint, thePoint).getBounds2D();
        return myShape;
    }

    @Override
    public Shape completeShape(final Point thePoint) {
        myShape = new Line2D.Double(myInitialPoint, thePoint).getBounds2D();
        return myShape;
    }

    @Override
    public Shape getShape() {
        return myShape;
    }
}
