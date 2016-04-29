/*
 * TCSS 305 Assignment 5 - PowerPaint
 */

package model;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.GeneralPath;

/**
 * A child of the AbstractShape that returns a GeneralPath Shape based on the
 * given points.
 * 
 * @author pcruz95
 * @version 2
 */
public class PencilShape extends AbstractShape {

    /**
     * The constructor for the PencilShape class.
     * 
     * @param theColor the PencilShape's color
     * @param theThickness the PencilShape's thickness
     */
    public PencilShape(final Color theColor, final int theThickness) {
        super(theColor, theThickness);
        myShape = new GeneralPath();
    }

    @Override
    public Shape press(final Point thePoint) {
        myShape = new GeneralPath();
        ((GeneralPath) myShape).moveTo(thePoint.getX(), thePoint.getY());
        return myShape;
    }

    @Override
    public Shape drag(final Point thePoint) {
        ((GeneralPath) myShape).lineTo(thePoint.getX(), thePoint.getY());
        return myShape;
    }

    @Override
    public Shape completeShape(final Point thePoint) {
        ((GeneralPath) myShape).lineTo(thePoint.getX(), thePoint.getY());
        return myShape;
    }

    @Override
    public Shape getShape() {
        return myShape;
    }

}
