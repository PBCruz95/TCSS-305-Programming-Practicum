/*
 * TCSS 305 Assignment 6 - Tetris
 */

package view;

import java.awt.EventQueue;

/**
 * This class cotains the program's main method.
 * 
 * @author prcruz95
 * @version 1
 */
public final class TetrisMain {

    /**
     * Private constructor for this class.
     */
    private TetrisMain() {

    }

    /**
     * The project's main method.
     * 
     * @param theArgs the arguments
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TetrisGUI().start();
            }
        });
    }

}
