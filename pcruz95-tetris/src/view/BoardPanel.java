/*
 * TCSS 305 Assignment 6 - Tetris
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import model.AbstractPiece;
import model.Block;
import model.Board;

/**
 * This class is a JPanel that will graphically display a game of Tetris.
 * 
 * @author pcruz95
 * @version 1
 *
 */
public class BoardPanel extends JPanel {

    /**
     * The generated serial ID for this class.
     */
    private static final long serialVersionUID = 8528622728467543765L;

    /**
     * Three pixels.
     */
    private static final int THREE = 3;

    /**
     * Four pixels.
     */
    private static final int FOUR = 4;

    /**
     * A boolean that returns true if the game is paused and cannot be
     * controlled until this returns false.
     */
    protected boolean myIsPaused;

    /**
     * The scaling that will help convert myWidth and myHeight from pixels to
     * squares.
     */
    protected final int myScaling;

    /**
     * The height of the board (in pixels).
     */
    private final int myHeight;

    /**
     * The board that will be played on.
     */
    private final Board myBoard;

    /**
     * The constructor for BoardPanel class.
     * 
     * @param theWidth the board's width
     * @param theHeight the board's height
     * @param theScaling the scaling to be used to convert pixels into squares
     */
    public BoardPanel(final int theWidth, final int theHeight, final int theScaling) {
        super();
        myHeight = theHeight;
        myScaling = theScaling;
        myBoard = new Board(theWidth, myHeight);
        this.setPreferredSize(new Dimension(theWidth * myScaling,
                                            myHeight * myScaling + myScaling + 1));
    }

    /**
     * Paints everything happening on the board.
     * 
     * @param theGraphics a Graphics instance for drawing on the panel
     */
    protected void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D graphics2d = (Graphics2D) theGraphics;
        graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
        paintCurrentPiece(graphics2d);
        paintFrozenBlocks(graphics2d);
        if (myIsPaused) {
            graphics2d.setColor(Color.WHITE);
            graphics2d.fillRect(THREE * myScaling, myHeight / THREE * myScaling,
                                FOUR * myScaling - 2, 2 * myScaling);
            graphics2d.setColor(Color.BLACK);
            graphics2d.drawRect(THREE * myScaling, myHeight / THREE * myScaling,
                                FOUR * myScaling - 2, 2 * myScaling);
            graphics2d.drawString("PAUSED", FOUR * myScaling + THREE,
                                  myHeight / THREE * myScaling + myScaling + 2);

        }
    }

    /**
     * Paints the current piece that is dropping down.
     * 
     * @param theGraphics a Graphics instance for drawing the current piece
     */
    private void paintCurrentPiece(final Graphics theGraphics) {
        final int[][] aPiece =
                        ((AbstractPiece) myBoard.getCurrentPiece()).getBoardCoordinates();
        for (final int[] aPieceBlock : aPiece) {
            theGraphics.setColor(Color.WHITE);
            theGraphics.fillRect(aPieceBlock[0] * myScaling,
                                 (myHeight - aPieceBlock[1]) * myScaling, myScaling,
                                 myScaling);
            theGraphics.setColor(Color.BLACK);
            theGraphics.drawRect(aPieceBlock[0] * myScaling,
                                 (myHeight - aPieceBlock[1]) * myScaling, myScaling,
                                 myScaling);
        }
    }

    /**
     * Paints the frozen blocks that have been dropped already.
     * 
     * @param theGraphics a Graphics instance for drawing the frozen blocks
     */
    private void paintFrozenBlocks(final Graphics theGraphics) {
        int row = 0;
        for (final Block[] pieceBlocks : myBoard.getFrozenBlocks()) {
            int col = 0;
            for (final Block aBlock : pieceBlocks) {
                if (aBlock != Block.EMPTY) {
                    theGraphics.setColor(Color.GRAY);
                    theGraphics.fillRect(col * myScaling, (myHeight - row) * myScaling,
                                         myScaling, myScaling);
                    theGraphics.setColor(Color.BLACK);
                    theGraphics.drawRect(col * myScaling, (myHeight - row) * myScaling,
                                         myScaling, myScaling);
                }
                col++;
            }
            row++;
        }
    }

    /**
     * Return myBoard.
     * 
     * @return myBoard
     */
    public Board getMyBoard() {
        return myBoard;
    }

    /**
     * Sets the pause boolean to true or false depending on its current state.
     */
    public void setPaused() {
        myIsPaused ^= true;
        repaint();
    }

}
