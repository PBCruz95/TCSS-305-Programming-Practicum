/*
 * TCSS 305 Assignment 5 - PowerPaint
 */

package view;

import com.sun.glass.events.KeyEvent;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

/**
 * This class creates the menu and tool bars for the GUI.
 * 
 * @author pcruz95
 * @version 3
 */
public class Bars {

    /**
     * The major increment ticks for the thickness JSlider.
     */
    private static final int MAJOR_INCREMENT = 5;

    /**
     * The ButtonGroup for my tool bar buttons.
     */
    private final ButtonGroup myToolBarGroup;

    /**
     * The ButtonGroup for my menu bar tool items.
     */
    private final ButtonGroup myMenuBarGroup;

    /**
     * The menu bar with to hold the tool items.
     */
    private final JMenu myToolMenu;

    /**
     * The tool bar to hold the tool buttons.
     */
    private final JToolBar myToolBar;

    /**
     * The color icon for the color menu item.
     */
    private ColorIcon myColorIcon;

    /**
     * A list containing the actions for my tool buttons.
     */
    private final List<Action> myToolActions = new ArrayList<>();

    /**
     * The DrawingArea to be art-ed on.
     */
    private final DrawingCanvas myCanvas;

    /**
     * The constructor for the Bars class.
     */
    public Bars() {
        myToolBarGroup = new ButtonGroup();
        myMenuBarGroup = new ButtonGroup();
        myToolMenu = new JMenu("Tools");
        myToolBar = new JToolBar();
        myCanvas = new DrawingCanvas();
        myToolActions.add(myCanvas.new PencilAction());
        myToolActions.add(myCanvas.new LineAction());
        myToolActions.add(myCanvas.new RectangleAction());
        myToolActions.add(myCanvas.new EllipseAction());

    }

    /**
     * Returns the DrawingArea JPanel being used.
     * 
     * @return a DrawingArea
     */
    protected DrawingCanvas getMyCanvas() {
        return myCanvas;
    }

    /**
     * Creates the complete menu bar at the top of the JFrame.
     * 
     * @return a JMenuBar
     */
    protected JMenuBar createMenuBar() {
        final JMenuBar menuBar = new JMenuBar();
        final JMenu fileMenu = new JMenu("File"); // File
        fileMenu.setMnemonic(KeyEvent.VK_F);
        final JMenuItem undoAll = new JMenuItem("Undo all changes", KeyEvent.VK_U); // UndoAll
        undoAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myCanvas.getmyFinishedShapes().clear();
                final AbstractButton button = (AbstractButton) theEvent.getSource();
                button.setEnabled(false);
                myCanvas.repaint();
            }
        });
        undoAll.setEnabled(false);
        myCanvas.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(final PropertyChangeEvent theEvt) {
                if (!myCanvas.getmyFinishedShapes().isEmpty()) {
                    undoAll.setEnabled(true);
                }
            }
        });
        final JMenuItem exitItem = new JMenuItem("Exit", KeyEvent.VK_X); // Exit
        exitItem.addActionListener(new PowerPaintGUI().new ExitAction());
        fileMenu.add(undoAll);
        fileMenu.add(new JSeparator());
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        final JMenu optionsMenu = new JMenu("Options"); // Options
        optionsMenu.setMnemonic(KeyEvent.VK_O);
        final JCheckBoxMenuItem gridItem = new JCheckBoxMenuItem("Grid"); // Grid
        gridItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myCanvas.toggleGridOn();
            }
        });
        gridItem.setMnemonic(KeyEvent.VK_G);
        final JMenu thicknessMenu = new JMenu("Thickness"); // Thickness
        thicknessMenu.setMnemonic(KeyEvent.VK_T);
        final JSlider thicknessSlider = new JSlider(0, 20);
        thicknessSlider.setMajorTickSpacing(MAJOR_INCREMENT);
        thicknessSlider.setMinorTickSpacing(1);
        thicknessSlider.setValue(1);
        thicknessSlider.setPaintLabels(true);
        thicknessSlider.setPaintTicks(true);
        thicknessSlider.addChangeListener(myCanvas.new ThicknessListener());
        thicknessMenu.add(thicknessSlider);
        myColorIcon = new ColorIcon(myCanvas.getMyCurrentShape().getColor());
        final JMenuItem colorItem = new JMenuItem("Color...", myColorIcon); // Color
        colorItem.setMnemonic(KeyEvent.VK_C);
        colorItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                final Color result = JColorChooser.
                                showDialog(null, "A Color Chooser",
                                            myCanvas.getMyCurrentShape().getColor());
                if (result != null) {
                    myCanvas.getMyCurrentShape().setColor(result);
                    myColorIcon = new ColorIcon(result);
                    colorItem.setIcon(myColorIcon);
                }
            }
        });
        optionsMenu.add(gridItem);
        optionsMenu.add(new JSeparator());
        optionsMenu.add(thicknessMenu);
        optionsMenu.add(new JSeparator());
        optionsMenu.add(colorItem);
        menuBar.add(optionsMenu);

        createToolMenu();
        myToolMenu.setMnemonic(KeyEvent.VK_T);
        menuBar.add(myToolMenu);

        final JMenu helpMenu = new JMenu("Help"); // Help
        helpMenu.setMnemonic(KeyEvent.VK_H);
        final JMenuItem aboutItem = new JMenuItem("About…", KeyEvent.VK_A); // About
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                JOptionPane.showMessageDialog(aboutItem, "TCSS 305 PowerPaint, Autumn 2015");
            }
        });
        helpMenu.add(aboutItem);
        menuBar.add(helpMenu);

        return menuBar;
    }

    /**
     * Creates a single radio button for the menu with all the tools.
     * 
     * @param theAction the action listener for a button
     */
    private void createToolMenuButton(final Action theAction) {
        final JRadioButtonMenuItem toolButton = new JRadioButtonMenuItem(theAction);
        myMenuBarGroup.add(toolButton);
        myToolMenu.add(toolButton);
        toolButton.setIcon(null);
    }

    /**
     * Creates the menu with all the tools.
     * 
     * @return a JMenu
     */
    private JMenu createToolMenu() {

        for (final Action a : myToolActions) {
            createToolMenuButton(a);
        }
        return myToolMenu;
    }

    /**
     * Creates a single tool bar button.
     * 
     * @param theAction the action listener for a button
     */
    private void createToolButton(final Action theAction) {
        final JToggleButton toolButton = new JToggleButton(theAction);
        myToolBarGroup.add(toolButton);
        myToolBar.add(toolButton);
    }

    /**
     * Creates the tool bar.
     * 
     * @return a JToolBar
     */
    protected JToolBar createToolBar() {
        for (final Action a : myToolActions) {
            createToolButton(a);
        }
        return myToolBar;
    }

    /**
     * The Icon to be displayed in the color menu item.
     * 
     * @author pcruz95
     *
     */
    public class ColorIcon implements Icon {

        /**
         * The height and width of the icon.
         */
        private static final int SIZE = 15;

        /**
         * The icon's color.
         */
        private final Color myColor;

        /**
         * The constructor for the ColorIcon inner class.
         * 
         * @param theColor the icon's color
         */
        public ColorIcon(final Color theColor) {
            myColor = theColor;
        }

        @Override
        public void paintIcon(final Component theComp, final Graphics theGraphics,
                              final int theX, final int theY) {
            theGraphics.setColor(myColor);
            theGraphics.fillRect(theX, theY, SIZE, SIZE);
        }

        @Override
        public int getIconWidth() {
            return SIZE;
        }

        @Override
        public int getIconHeight() {
            return SIZE;
        }

    }
}
