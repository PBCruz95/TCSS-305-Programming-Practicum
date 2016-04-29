/*
 * TCSS 305 Assignment 6 - Tetris
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.Board;

/**
 * This class puts everything together into a JFrame and contains the main
 * method to run this Tetris clone program.
 * 
 * @author pcruz95
 * @version 1
 *
 */
public class TetrisGUI extends JFrame implements Observer {

    /**
     * The generated serial ID for this class.
     */
    private static final long serialVersionUID = -8346131397136956458L;

    /**
     * The dimensions of the game board.
     */
    private static final Dimension BOARD_DIMENSIONS = new Dimension(10, 20);

    /**
     * The scaling to convert pixels into squares. I like the number 30.
     */
    private static final int SCALING = 30;

    /**
     * The timer's delay in milliseconds.
     */
    private static final int TIMER_DELAY = 1000;

    /**
     * The BoardPanel that will draw out a Tetris game that is happening.
     */
    private final BoardPanel myBoardPanel;

    /**
     * The NextPiecePanel that will show what the next piece to be played will
     * be.
     */
    private final NextPiecePanel myNextPiecePanel;

    /**
     * The ScorePanel that shows the current level, the score, lines cleared,
     * and how many more lines must be cleared to level up.
     */
    private final ScorePanel myScorePanel;

    /**
     * The board that will be played on.
     */
    private final Board myBoard;

    /**
     * The menu bar that is set on this frame.
     */
    private final TetrisMenuBar myMenuBar;

    /**
     * The timer that will control the game's animation.
     */
    private final Timer myTimer;

    /**
     * The constructor for the TetrisGUI class.
     */
    public TetrisGUI() {
        super();
        this.setTitle("TCSS 305 Tetris");
        myTimer = new Timer(TIMER_DELAY, new StepListener());
        myBoardPanel = new BoardPanel(BOARD_DIMENSIONS.width, BOARD_DIMENSIONS.height,
                                      SCALING);
        myBoard = myBoardPanel.getMyBoard();
        myNextPiecePanel = new NextPiecePanel(myBoard, SCALING);
        myScorePanel = new ScorePanel(myBoard, myTimer);
        myMenuBar = new TetrisMenuBar(myBoardPanel, myScorePanel, myTimer);
        myBoard.addObserver(this);
        addKeyListener(new ControlsListener(myBoardPanel, myMenuBar, myTimer));
    }

    /**
     * Sets up all of the components and organizes them into this JFrame.
     */
    public void setUpComponents() {
        myMenuBar.createMenuBar();
        this.setJMenuBar(myMenuBar);
        this.setLayout(new FlowLayout());
        myBoardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.add(myBoardPanel);
        final JPanel rightPanel = new JPanel(new GridLayout(3, 0));
        myNextPiecePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        rightPanel.add(myNextPiecePanel);
        rightPanel.add(myScorePanel);
        final JLabel controlsLabel = new JLabel();
        controlsLabel.setText("<html><body>Controls:<br><br>Move Left - Left Arrow"
                              + "<br>Move Right - Right Arrow<br>Move Down - "
                              + "Down Arrow<br>Drop - Spacebar" + "<br>Rotate - Up Arrow"
                              + "<br>Pause - P</body></html>");
        rightPanel.add(controlsLabel);
        this.add(rightPanel);
    }

    /**
     * Calling this method effectively starts the entire program.
     */
    public void start() {
        setDefaultLookAndFeelDecorated(true);
        this.setUpComponents();
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void update(final Observable theObs, final Object theArgs) {
        myNextPiecePanel.setGameStarted(true);
        if (myBoard.isGameOver()) {
            myTimer.stop();
            JOptionPane.showMessageDialog(this, "Try again!", "GAME OVER",
                                          JOptionPane.INFORMATION_MESSAGE);
            myMenuBar.getMyNewGameItem().setEnabled(true);
            myMenuBar.getMyEndGameItem().setEnabled(false);
        }
        repaint();
    }

    /**
     * This inner class progresses the game each when the timer ticks if the
     * game has not ended yet.
     * 
     * @author pcruz95
     *
     */
    public class StepListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myBoard.step();
        }
    }

    /**
     * Terminates the program.
     * 
     * @author pcruz95
     *
     */
    public class ExitAction extends AbstractAction {

        /**
         * The serial version ID of this class.
         */
        private static final long serialVersionUID = -2288655310616715252L;

        /**
         * The constructor for the ExitAction inner class.
         */
        public ExitAction() {
            super("Exit");
            putValue(MNEMONIC_KEY, KeyEvent.VK_X);
            putValue(SELECTED_KEY, true);
        }

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            System.exit(0);
        }
    }
}
