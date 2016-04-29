/*
 * TCSS 305 Assignment 6 - Tetris
 */

package view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Timer;

import model.Board;

/**
 * This class sets the controls for the CONTROLS hashmap and performs a
 * particular action depending on which value in CONTROLS matches the pressed
 * key value.
 * 
 * @author pcruz95
 * @version 2
 *
 */
public class ControlsListener extends KeyAdapter {
    // TODO add ability to change controls later
    /**
     * String that represents down.
     */
    private static final String DOWN = "down";

    /**
     * String that represents drop.
     */
    private static final String DROP = "drop";

    /**
     * String that represents left.
     */
    private static final String LEFT = "left";

    /**
     * String that represents right.
     */
    private static final String RIGHT = "right";

    /**
     * String that represents rotate.
     */
    private static final String ROTATE = "rotate";

    /**
     * String that represents pause.
     */
    private static final String PAUSE = "pause";

    /**
     * The BoardPanel that will draw out a Tetris game that is happening.
     */
    private final BoardPanel myBoardPanel;

    /**
     * The board that will be played on.
     */
    private final Board myBoard;

    /**
     * The menu bar that is set on this frame. It is here because it is able to
     * end the game, and I want the controls disabled when it ends the game.
     */
    private final TetrisMenuBar myMenuBar;

    /**
     * The timer that will control the game's animation.
     */
    private final Timer myTimer;

    /**
     * A hashmap containing the key controls for this program. Default controls
     * are provided.
     */
    private final Map<String, Integer> myControls = new HashMap<String, Integer>();

    /**
     * The array that contains the controls.
     */
    private final String[] myControlNames = {DOWN, DROP, LEFT, RIGHT, ROTATE, PAUSE};

    /**
     * The array that holds the default key events assigned to controls.
     * 
     */
    private final int[] myControlKeys = {KeyEvent.VK_DOWN, KeyEvent.VK_SPACE, KeyEvent.VK_LEFT,
        KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_P};

    /**
     * The constructor for the ControlsListener inner class.
     * 
     * @param theBP the board panel used
     * @param theMBar the menubar being used
     * @param theTimer the timer that controls game animation
     */
    public ControlsListener(final BoardPanel theBP, final TetrisMenuBar theMBar,
                            final Timer theTimer) {
        super();
        myBoardPanel = theBP;
        myBoard = myBoardPanel.getMyBoard();
        myMenuBar = theMBar;
        myTimer = theTimer;
        for (int i = 0; i < myControlNames.length; i++) {
            myControls.put(myControlNames[i], myControlKeys[i]);
        }
    }

    @Override
    public void keyPressed(final KeyEvent theEvent) {
        if (!myMenuBar.isGameOver() && !myBoard.isGameOver()) {
            pieceControls(theEvent);
        }
    }

    /**
     * The controls to be used on the current piece and the pause action.
     * 
     * @param theEvent the key event that determines what will be done to
     *            current piece and pause
     */
    public void pieceControls(final KeyEvent theEvent) {
        if (!myBoardPanel.myIsPaused) {
            if (theEvent.getKeyCode() == myControls.get(DOWN)) {
                myBoard.moveDown();
            } else if (theEvent.getKeyCode() == myControls.get(DROP)) {
                myBoard.hardDrop();
            } else if (theEvent.getKeyCode() == myControls.get(LEFT)) {
                myBoard.moveLeft();
            } else if (theEvent.getKeyCode() == myControls.get(RIGHT)) {
                myBoard.moveRight();
            } else if (theEvent.getKeyCode() == myControls.get(ROTATE)) {
                myBoard.rotate();
            }
        }
        if (theEvent.getKeyCode() == myControls.get(PAUSE)) {
            keyPause();
        }
    }

    /**
     * This method pauses the game.
     */
    public void keyPause() {
        if (myBoardPanel.myIsPaused) {
            myTimer.start();
            myBoardPanel.setPaused();
        } else {
            myTimer.stop();
            myBoardPanel.setPaused();
        }

    }
}
