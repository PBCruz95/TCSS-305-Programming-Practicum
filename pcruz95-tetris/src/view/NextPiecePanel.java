/*
 * TCSS 305 Assignment 6 - Tetris
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.AbstractPiece;
import model.Board;

/**
 * This class is a JPanel that displays what the next piece to be used on the
 * given Board will be.
 * 
 * @author pcruz95
 * @version 1
 *
 */
public class NextPiecePanel extends JPanel implements Observer {

    /**
     * The generated serial ID for this class.
     */
    private static final long serialVersionUID = -4023628052476953899L;
    /**
     * The length of one of this panel's sides, in pixels.
     */
    private static final int NEXT_PANEL_DIMENSION = 5;

    /**
     * The scaling to convert pixels into squares. I like the number 30.
     */
    private final int myScaling;

    /**
     * The Board that will be played on.
     */
    private final Board myBoard;

    /**
     * Returns true if a game has started.
     */
    private boolean myGameHasStarted;

    /**
     * The constructor for the NextPiecePanel class.
     * 
     * @param theBoard the board being played on
     * @param theScaling the scaling to be used to convert pixels into squares
     */
    public NextPiecePanel(final Board theBoard, final int theScaling) {
        super();
        myBoard = theBoard;
        myScaling = theScaling;
        myBoard.addObserver(this);
        this.setPreferredSize(new Dimension(NEXT_PANEL_DIMENSION * myScaling + myScaling,
                                            NEXT_PANEL_DIMENSION * myScaling));

    }

    /**
     * Paints the next piece to be used onto this panel.
     */
    @Override
    protected void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D graphics2d = (Graphics2D) theGraphics;
        graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
        if (myGameHasStarted) {
            final int[][] aPiece = ((AbstractPiece) myBoard.getNextPiece()).getRotation();
            for (final int[] aPieceBlock : aPiece) {
                graphics2d.setColor(Color.WHITE);
                graphics2d.fillRect(aPieceBlock[0] * myScaling + myScaling,
                                    (-aPieceBlock[1] + 1) * myScaling + myScaling * 2,
                                    myScaling, myScaling);
                graphics2d.setColor(Color.BLACK);
                graphics2d.drawRect(aPieceBlock[0] * myScaling + myScaling,
                                    (-aPieceBlock[1] + 1) * myScaling + myScaling * 2,
                                    myScaling, myScaling);
            }
        }
    }

    /**
     * Updates the next piece that will be used.
     */
    @Override
    public void update(final Observable theObs, final Object theArgs) {
        repaint();
    }

    /**
     * Sets myGameHasStarted to true if a game has started.
     * 
     * @param theState true if game has started
     */
    public void setGameStarted(final boolean theState) {
        myGameHasStarted = theState;
    }
}
