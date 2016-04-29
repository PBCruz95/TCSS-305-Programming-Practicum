/*
 * TCSS 305 Assignment 5 - PowerPaint
 */

package view;

import com.sun.glass.events.KeyEvent;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * This class creates a GUI from the other classes in this project for the user
 * to interact with.
 * 
 * @author pcruz95
 * @version 2
 */
public class PowerPaintGUI extends JFrame {

    /**
     * The serial Version ID of this class.
     */
    private static final long serialVersionUID = 3650553337266286532L;

    /**
     * The constructor for the PowerPaintGUI class.
     */
    public PowerPaintGUI() {
        super("PowerPaint");
    }

    /**
     * Sets up the multiple components within this JFrame.
     */
    public void setUpComponents() {
        final Bars bars = new Bars();
        setLayout(new BorderLayout());
        setJMenuBar(bars.createMenuBar());
        add(bars.getMyCanvas());
        add(bars.createToolBar(), BorderLayout.SOUTH);

    }

    /**
     * Puts everything together and sets the GUI visible for the user.
     */
    public void start() {
        setDefaultLookAndFeelDecorated(true);
        this.setUpComponents();
        this.pack();
        this.setMinimumSize(this.getSize());
        final ImageIcon icon = new ImageIcon("images/w.gif");
        this.setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Where the project's main method.
     * 
     * @param theArgs the arguments
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PowerPaintGUI().start();
            }
        });
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
