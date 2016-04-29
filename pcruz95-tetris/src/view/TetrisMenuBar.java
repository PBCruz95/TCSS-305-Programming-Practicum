/*
 * TCSS 305 Assignment 6 - Tetris
 */

package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import model.Board;

/**
 * This class is the menu bar for the GUI.
 * 
 * @author pcruz95
 * @version 1
 *
 */
public class TetrisMenuBar extends JMenuBar {

    /**
     * The generated serial ID for this class.
     */
    private static final long serialVersionUID = -8436264661459312329L;

    /**
     * The board that will be played on.
     */
    private final Board myBoard;

    /**
     * The BoardPanel that will draw out a Tetris game that is happening.
     */
    private final BoardPanel myBoardPanel;

    /**
     * The ScorePanel that displays a bunch of important numbers.
     */
    private final ScorePanel myScorePanel;

    /**
     * The timer that will control the game's animation.
     */
    private final Timer myTimer;

    /**
     * The JMenuItem that creates a new instance of a game.
     */
    private JMenuItem myNewGameItem;

    /**
     * The JMenuItem that ends the current game instance.
     */
    private JMenuItem myEndGameItem;

    /**
     * A boolean that returns true if the game is over.
     */
    private boolean myIsGameOver = true;

    /**
     * A boolean that returns true once a game has started. It never goes back
     * to false.
     */
    private boolean myGameHasStarted;

    /**
     * The constructor for the TetrisMenuBar class.
     * 
     * @param theBP the board panel
     * @param theSP the score panel
     * @param theTimer the timer to control animation
     */
    public TetrisMenuBar(final BoardPanel theBP, final ScorePanel theSP,
                         final Timer theTimer) {
        super();
        myBoardPanel = theBP;
        myScorePanel = theSP;
        myBoard = myBoardPanel.getMyBoard();
        myTimer = theTimer;
    }

    /**
     * Creates the menu bar to be used for this program.
     */
    public void createMenuBar() {
        final JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        this.add(fileMenu);
        myNewGameItem = new JMenuItem("New Game", KeyEvent.VK_N);
        myNewGameItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myScorePanel.reset();
                myIsGameOver = false;
                myGameHasStarted = true;
                myBoard.newGame(myBoard.getWidth(), myBoard.getHeight(), null);
                myTimer.start();
                myNewGameItem.setEnabled(false);
                myEndGameItem.setEnabled(true);
            }
        });

        fileMenu.add(myNewGameItem);
        fileMenu.addSeparator();
        myEndGameItem = new JMenuItem("End Game", KeyEvent.VK_E);
        myEndGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myIsGameOver = true;
                myBoardPanel.myIsPaused = false;
                myBoardPanel.repaint();
                myTimer.stop();
                JOptionPane.showMessageDialog(myEndGameItem, "What a quitter!", "GAME OVER",
                                              JOptionPane.INFORMATION_MESSAGE);
                myNewGameItem.setEnabled(true);
                myEndGameItem.setEnabled(false);
            }
        });
        myEndGameItem.setEnabled(false);
        fileMenu.add(myEndGameItem);
        fileMenu.addSeparator();
        final JMenuItem exitItem = new JMenuItem("Exit", KeyEvent.VK_X);
        exitItem.addActionListener(new TetrisGUI().new ExitAction());
        fileMenu.add(exitItem);
        final JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);
        this.add(helpMenu);
        final JMenuItem scoringItem = new JMenuItem("Scoring...", KeyEvent.VK_S);
        scoringItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                JOptionPane.showMessageDialog(scoringItem,
                                              "<html><bod><br>Starting"
                                                           + " a game: 100 (you earned it!)"
                                                           + "<br>Placing a piece: 100<br>"
                                                           + "Clearing a line: 500<br><br>"
                                                           + "Everything above is multiplied"
                                                           + " by the current level.</bod>"
                                                           + "</html>",
                                              "Scoring", JOptionPane.INFORMATION_MESSAGE);

            }

        });
        helpMenu.add(scoringItem);
        helpMenu.addSeparator();
        final JMenuItem aboutItem = new JMenuItem("About...", KeyEvent.VK_A);
        aboutItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                JOptionPane.showMessageDialog(aboutItem,
                                              "<html><body><br>This "
                                                         + "is a Tetris clone whose front "
                                                         + "end was <br>devoloped by Patrick"
                                                         + " Cruz for TCSS 305<br>at UW,"
                                                         + " Tacoma for Autumn 2015. This "
                                                         + "<br>class was really fun "
                                                         + "and really helped"
                                                         + "<br>set up a solid "
                                                         + "foundation for my "
                                                         + "<br>programming ability."
                                                         + "<br><br> Thanks for an "
                                                         + "awesome quarter, Mr. Bryan!",
                                              "About", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        helpMenu.add(aboutItem);
    }

    /**
     * Returns the new game menu item that starts a new game.
     * 
     * @return myNewGameItem
     */
    public JMenuItem getMyNewGameItem() {
        return myNewGameItem;
    }

    /**
     * Returns the end game menu item that ends the current game.
     * 
     * @return myEndGameItem
     */
    public JMenuItem getMyEndGameItem() {
        return myEndGameItem;
    }

    /**
     * Returns true if the game is over, false if it isn't.
     * 
     * @return myIsGameOver
     */
    public boolean isGameOver() {
        return myIsGameOver;
    }

    /**
     * Returns true if a game has started.
     * 
     * @return myGameHasStarted
     */
    public boolean gameHasStarted() {
        return myGameHasStarted;
    }
}
