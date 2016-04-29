/*
 * TCSS 305 Assignment 5 - PowerPaint
 */

package model;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 * A child of the AbstractShape that returns an Ellipse2D.Double Shape based on
 * the given points.
 * 
 * @author pcruz95
 * @version 1
 */
public class EllipseShape extends AbstractShape {

    /**
     * The initial point when the mouse is first pressed.
     */
    private Point myInitialPoint;

    /**
     * The rectangle that will take in the given points. This rectangle is
     * immediately converted into an ellipse in the methods below.
     */
    private Rectangle2D myRectangle;

    /**
     * The constructor for the EllipseShape class.
     * 
     * @param theColor the EllipseShape's color
     * @param theThickness the EllipseShape's thickness
     */
    public EllipseShape(final Color theColor, final int theThickness) {
        super(theColor, theThickness);
    }

    @Override
    public Shape press(final Point thePoint) {
        myInitialPoint = thePoint;
        myRectangle = new Line2D.Double(myInitialPoint, myInitialPoint).getBounds2D();
        myShape = new Ellipse2D.Double(myRectangle.getX(), myRectangle.getY(),
                                       myRectangle.getWidth(), myRectangle.getHeight());
        return myShape;
    }

    @Override
    public Shape drag(final Point thePoint) {
        myRectangle = new Line2D.Double(myInitialPoint, thePoint).getBounds2D();
        myShape = new Ellipse2D.Double(myRectangle.getX(), myRectangle.getY(),
                                       myRectangle.getWidth(), myRectangle.getHeight());
        return myShape;
    }

    @Override
    public Shape completeShape(final Point thePoint) {
        myRectangle = new Line2D.Double(myInitialPoint, thePoint).getBounds2D();
        myShape = new Ellipse2D.Double(myRectangle.getX(), myRectangle.getY(),
                                       myRectangle.getWidth(), myRectangle.getHeight());
        return myShape;
    }

    @Override
    public Shape getShape() {
        return myShape;
    }
}
