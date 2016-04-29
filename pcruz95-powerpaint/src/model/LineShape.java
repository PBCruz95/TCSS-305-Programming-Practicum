/*
 * TCSS 305 Assignment 5 - PowerPaint
 */

package model;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Line2D;

/**
 * A child of the AbstractShape that returns a Line2D Shape based on the given
 * points.
 * 
 * @author pcruz95
 * @version 2
 */
public class LineShape extends AbstractShape {

    /**
     * The initial point when the mouse is first pressed.
     */
    private Point myInitialPoint;

    /**
     * The constructor for the LineShape class.
     * 
     * @param theColor the LineShape's color
     * @param theThickness the LineShape's thickness
     */
    public LineShape(final Color theColor, final int theThickness) {
        super(theColor, theThickness);
    }

    @Override
    public Shape press(final Point thePoint) {
        myInitialPoint = thePoint;
        myShape = new Line2D.Double(myInitialPoint, myInitialPoint);
        return myShape;
    }

    @Override
    public Shape drag(final Point thePoint) {
        ((Line2D) myShape).setLine(myInitialPoint, thePoint);
        return myShape;
    }

    @Override
    public Shape completeShape(final Point thePoint) {
        ((Line2D) myShape).setLine(myInitialPoint, thePoint);
        return myShape;
    }

    @Override
    public Shape getShape() {
        return myShape;
    }
}
