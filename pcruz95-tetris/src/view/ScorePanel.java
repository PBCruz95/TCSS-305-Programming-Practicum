/*
 * TCSS 305 Assignment 6 - Tetris
 */

package view;

import java.awt.GridLayout;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.Block;
import model.Board;
import model.Piece;

/**
 * This panel displays the player's current score and level.
 * 
 * @author pcruz95
 * @version 1
 */
public class ScorePanel extends JPanel implements Observer {

    /**
     * The generated serial ID for this class.
     */
    private static final long serialVersionUID = -8496205341697409306L;

    /**
     * The number of rows in this panel's grid layout.
     */
    private static final int ROWS = 4;

    /**
     * The initial number of lines needed to advance a level when a level
     * initially starts.
     */
    private static final int LINES_NEXT_LEVEL_INITIAL = 5;

    /**
     * Points scored per piece.
     */
    private static final int PIECE_SCORE = 100;

    /**
     * Points scored per line.
     */
    private static final int LINE_SCORE = 500;

    /**
     * String for level JLabel.
     */
    private static final String LEVEL = "Level: ";

    /**
     * String for score JLabel.
     */
    private static final String SCORE = "Score: ";

    /**
     * String for lines cleared JLabel.
     */
    private static final String LINES_CLEARED = "Lines Cleared: ";

    /**
     * String for lines 'til next level JLabel.
     */
    private static final String LINES_NEXT_LEVEL = "Lines Until Next Level: ";

    /**
     * The amount of the time the timer decrements every level up in
     * milliseconds.
     */
    private static final int TIMER_DECREMENT = 100;

    /**
     * The amount of time the timer decrements every level up in milliseconds
     * once the timer's delay reaches 100.
     */
    private static final int TIMER_DECREMENT_TWO = 10;

    /**
     * The timer will never decrement below 50 milliseconds.
     */
    private static final int TIMER_LIMIT = 50;

    /**
     * The board that will be played on.
     */
    private final Board myBoard;

    /**
     * The timer that will control the game's animation.
     */
    private final Timer myTimer;

    /**
     * The board's current piece.
     */
    private Piece myCurrentPiece;

    /**
     * JLabel that indicates the current level.
     */
    private JLabel myLevel;

    /**
     * JLabel that indicates the user's current score.
     */
    private JLabel myScore;

    /**
     * JLabel that indicates the user's number of lines cleared.
     */
    private JLabel myLinesCleared;

    /**
     * JLabel that indicates how many more lines the user must clear until a
     * level up.
     */
    private JLabel myLinesUntilNextLevel;

    /**
     * Int that represents the current level.
     */
    private int myLevelInt = 1;

    /**
     * Int that represents the current score.
     */
    private int myScoreInt;

    /**
     * Int that represents the number of lines cleared.
     */
    private int myLinesClearedInt;

    /**
     * Int that represents how many more lines are needed to level up.
     */
    private int myLinesUntilNextInt = LINES_NEXT_LEVEL_INITIAL;

    /**
     * The constructor for the ScorePanel class.
     * 
     * @param theBoard the board to be played on
     * @param theTimer the timer controlling animation
     */
    public ScorePanel(final Board theBoard, final Timer theTimer) {
        super();
        this.setLayout(new GridLayout(ROWS, 0));
        myBoard = theBoard;
        myCurrentPiece = myBoard.getCurrentPiece();
        myTimer = theTimer;
        addLabels();

    }

    /**
     * Adds the JLabels to this panel.
     */
    private void addLabels() {
        myBoard.addObserver(this);
        myLevel = new JLabel(LEVEL + myLevelInt);
        add(myLevel);
        myScore = new JLabel(SCORE + myScoreInt);
        add(myScore);
        myLinesCleared = new JLabel(LINES_CLEARED + myLinesClearedInt);
        add(myLinesCleared);
        myLinesUntilNextLevel = new JLabel(LINES_NEXT_LEVEL + myLinesUntilNextInt);
        add(myLinesUntilNextLevel);
    }

    @Override
    public void update(final Observable theObs, final Object theArgs) {
        calculatePiece();
        calculateLine();
        myLevel.setText(LEVEL + myLevelInt);
        myScore.setText(SCORE + myScoreInt);
        myLinesCleared.setText(LINES_CLEARED + myLinesClearedInt);
        myLinesUntilNextLevel.setText(LINES_NEXT_LEVEL + myLinesUntilNextInt);
        repaint();

    }

    /**
     * Calculates level, scoring with lines, lines cleared, and lines until next
     * level.
     */
    public void calculateLine() {
        final List<Block[]> frozenBlocks = myBoard.getFrozenBlocks();
        for (int i = frozenBlocks.size() - 1; i >= 0; i--) {
            boolean clear = true;
            final Block[] blocks = frozenBlocks.get(i);

            for (final Block block : blocks) {
                if (block == Block.EMPTY) {
                    clear = false;
                    break;
                }
            }
            if (clear) {
                frozenBlocks.remove(i);
                myLinesClearedInt++;
                myScoreInt += LINE_SCORE * myLevelInt;
                myLinesUntilNextInt--;
                if (myLinesUntilNextInt == 0) {
                    myLinesUntilNextInt = LINES_NEXT_LEVEL_INITIAL;
                    myLevelInt++;
                    timerDecrement();
                }
            }
        }
    }

    /**
     * Decrements the timer during level ups.
     */
    public void timerDecrement() {
        if (myTimer.getDelay() > TIMER_DECREMENT) {
            myTimer.setDelay(myTimer.getDelay() - TIMER_DECREMENT);
        } else if (myTimer.getDelay() > TIMER_LIMIT) {
            myTimer.setDelay(myTimer.getDelay() - TIMER_DECREMENT_TWO);
        }

    }

    /**
     * Calculates each piece when it is dropped.
     */
    public void calculatePiece() {
        final Piece currentPiece = myBoard.getCurrentPiece();
        if (!myCurrentPiece.equals(currentPiece)) {
            myScoreInt += PIECE_SCORE * myLevelInt;
            myCurrentPiece = currentPiece;
        }
    }

    /**
     * Resets all of the numbers.
     */
    public void reset() {
        myLevelInt = 1;
        myScoreInt = 0;
        myLinesClearedInt = 0;
        myLinesUntilNextInt = LINES_NEXT_LEVEL_INITIAL;
        repaint();
    }
}
