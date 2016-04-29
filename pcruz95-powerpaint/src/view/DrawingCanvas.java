/*
 * TCSS 305 Assignment 5 - PowerPaint
 */

package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputAdapter;

import model.DrawShape;
import model.EllipseShape;
import model.FinishedShape;
import model.LineShape;
import model.PencilShape;
import model.RectangleShape;

/**
 * This is the JPanel where all the drawing is done and changes are reflected
 * on.
 * 
 * @author pcruz95
 * @version 3
 */
public class DrawingCanvas extends JPanel {

    /**
     * The serial Version ID of this class.
     */
    private static final long serialVersionUID = 39833714781447755L;

    /**
     * The preferred and initial size of the JPanel.
     */
    private static final Dimension PREFERRED_SIZE = new Dimension(400, 200);

    /**
     * The amount of pixels each grid line is apart.
     */
    private static final int GRID_INCREMENT = 10;

    /**
     * The current shape being drawn.
     */
    private DrawShape myCurrentShape;

    /**
     * A collection of finished shapes, each one being added after the mouse is
     * released.
     */
    private final List<FinishedShape> myFinishedShapes = new ArrayList<>();

    /**
     * A boolean that returns true if a shape is being dragged.
     */
    private boolean myDragOn;

    /**
     * A boolean that switches whenever the GridAction listener is triggered.
     */
    private boolean myGridOn;

    /**
     * Constructor for the DrawingArea class.
     */
    public DrawingCanvas() {
        super();
        setPreferredSize(PREFERRED_SIZE);
        setBackground(Color.WHITE);
        addMouseListener(new MyMouseInputAdapter());
        addMouseMotionListener(new MyMouseInputAdapter());
        myCurrentShape = new PencilShape(Color.BLACK, 1);

    }

    @Override
    protected void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D graphics2d = (Graphics2D) theGraphics;
        graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
        for (final FinishedShape aShape : myFinishedShapes) {
            graphics2d.setStroke(new BasicStroke(aShape.getThickness()));
            graphics2d.setColor(aShape.getColor());
            graphics2d.draw(aShape.getShape());
        }
        if (myDragOn) {
            graphics2d.setStroke(new BasicStroke(myCurrentShape.getThickness()));
            graphics2d.setColor(myCurrentShape.getColor());
            graphics2d.draw(myCurrentShape.getShape());
        }
        if (myGridOn) {
            graphics2d.setStroke(new BasicStroke(1));
            graphics2d.setPaint(Color.GRAY);
            for (int x = 0; x < this.getWidth(); x += GRID_INCREMENT) {
                graphics2d.drawLine(x, 0, x, this.getHeight());
            }
            for (int y = 1; y < this.getHeight(); y += GRID_INCREMENT) {
                graphics2d.drawLine(0, y, this.getWidth(), y);
            }
        }
    }

    /**
     * Switches the myGridOn boolean, which determines whether a grid will be
     * drawn or not.
     */
    public void toggleGridOn() {
        myGridOn ^= true;
        repaint();
    }

    /**
     * Returns the List containing all finished shapes.
     * 
     * @return List of all finished shapes.
     */
    public List<FinishedShape> getmyFinishedShapes() {
        return myFinishedShapes;
    }

    /**
     * Returns the current shape to be drawn.
     * 
     * @return myCurrentShape
     */
    public DrawShape getMyCurrentShape() {
        return myCurrentShape;
    }

    /**
     * The MouseInputAdapter that will handle all the necessary mouse activities
     * in the JPanel.
     * 
     * @author pcruz95
     *
     */
    class MyMouseInputAdapter extends MouseInputAdapter {

        @Override
        public void mouseEntered(final MouseEvent theEvent) {
            setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        }

        @Override
        public void mousePressed(final MouseEvent theEvent) {
            myDragOn = true;
            myCurrentShape.press(theEvent.getPoint());
            repaint();
        }

        @Override
        public void mouseDragged(final MouseEvent theEvent) {
            myCurrentShape.drag(theEvent.getPoint());
            repaint();
        }

        @Override
        public void mouseReleased(final MouseEvent theEvent) {
            myDragOn = false;
            myCurrentShape.completeShape(theEvent.getPoint());
            myFinishedShapes.add(new FinishedShape(myCurrentShape));
            firePropertyChange("LIST_NOT_EMPTY", myFinishedShapes.isEmpty(),
                               !myFinishedShapes.isEmpty());

            repaint();
        }
    }

    /**
     * Makes myCurrentShape a new PencilShape.
     * 
     * @author pcruz95
     *
     */
    public class PencilAction extends AbstractAction {

        /**
         * The serial version ID of this class.
         */
        private static final long serialVersionUID = -4537011073989788879L;

        /**
         * Constructor for the PencilAction inner class.
         */
        public PencilAction() {
            super("Pencil", new ImageIcon("images/pencil_bw.gif"));
            putValue(MNEMONIC_KEY, KeyEvent.VK_P);
            putValue(SELECTED_KEY, true);
        }

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myCurrentShape = new PencilShape(myCurrentShape.getColor(),
                                             myCurrentShape.getThickness());
        }

    }

    /**
     * Makes myCurrentShape a new LineShape.
     * 
     * @author pcruz95
     *
     */
    public class LineAction extends AbstractAction {

        /**
         * The serial Version ID of this class.
         */
        private static final long serialVersionUID = -3742594275371668220L;

        /**
         * Constructor for the LineAction inner class.
         */
        public LineAction() {
            super("Line", new ImageIcon("images/line_bw.gif"));
            putValue(MNEMONIC_KEY, KeyEvent.VK_L);
            putValue(SELECTED_KEY, true);
        }

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myCurrentShape = new LineShape(myCurrentShape.getColor(),
                                           myCurrentShape.getThickness());
        }

    }

    /**
     * Makes myCurrentShape a new RectangleShape.
     * 
     * @author pcruz95
     *
     */
    public class RectangleAction extends AbstractAction {

        /**
         * The serial version ID of this class.
         */
        private static final long serialVersionUID = -7645285620778821648L;

        /**
         * Constructor for the RectangleAction inner class.
         */
        public RectangleAction() {
            super("Rectangle", new ImageIcon("images/rectangle_bw.gif"));
            putValue(MNEMONIC_KEY, KeyEvent.VK_R);
            putValue(SELECTED_KEY, true);
        }

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myCurrentShape = new RectangleShape(myCurrentShape.getColor(),
                                                myCurrentShape.getThickness());
        }
    }

    /**
     * Makes myCurrentShape a new EllipseShape.
     * 
     * @author pcruz95
     *
     */
    public class EllipseAction extends AbstractAction {

        /**
         * The serial version ID of this class.
         */
        private static final long serialVersionUID = 2495320852149041261L;

        /**
         * Constructor for the EllipseAction inner class.
         */
        public EllipseAction() {
            super("Ellipse", new ImageIcon("images/ellipse_bw.gif"));
            putValue(MNEMONIC_KEY, KeyEvent.VK_E);
            putValue(SELECTED_KEY, true);
        }

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myCurrentShape = new EllipseShape(myCurrentShape.getColor(),
                                              myCurrentShape.getThickness());
        }
    }

    /**
     * Changes the color that myCurrentShape will use when drawn by calling up a
     * JColorChooser to prompt the user which color to use.
     * 
     * @author pcruz95
     *
     */
    public class ColorAction extends AbstractAction {

        /**
         * The serial version ID of this class.
         */
        private static final long serialVersionUID = -1412531516796439318L;

        /**
         * Constructor for the ColorAction inner class.
         */
        public ColorAction() {
            super("Color");
            putValue(MNEMONIC_KEY, KeyEvent.VK_C);
            putValue(SELECTED_KEY, true);
        }

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            final Color result = JColorChooser.showDialog(null, "A Color Chooser", null);
            if (result != null) {
                myCurrentShape.setColor(result);
            }

        }

    }

    /**
     * Changes the thickness of myCurrentShape if there is a change to the
     * JSlider found in the Bars class.
     * 
     * @author pcruz95
     *
     */
    public class ThicknessListener implements ChangeListener {

        @Override
        public void stateChanged(final ChangeEvent theEvent) {
            final JSlider source = (JSlider) theEvent.getSource();
            if (!source.getValueIsAdjusting()) {
                myCurrentShape.setThickness(source.getValue());

            }

        }

    }
}
