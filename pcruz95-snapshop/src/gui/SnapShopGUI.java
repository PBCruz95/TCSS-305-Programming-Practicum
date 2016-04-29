/*
 * TCSS 305 Assignment 4 - Snap Shop
 */

package gui;

import filters.EdgeDetectFilter;
import filters.EdgeHighlightFilter;
import filters.Filter;
import filters.FlipHorizontalFilter;
import filters.FlipVerticalFilter;
import filters.GrayscaleFilter;
import filters.SharpenFilter;
import filters.SoftenFilter;
import image.PixelImage;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Creates a GUI for the SnapShop Project.
 * 
 * @author pcruz95
 * @version version 5
 */
public class SnapShopGUI extends JFrame {

    /**
     * A generated serial version UID for object Serialization.
     */
    private static final long serialVersionUID = 4088911325611485013L;

    /**
     * A string used for a check for the OpenButtonListener and
     * CloseButtonListener.
     */
    private static final String GIF = ".gif";

    /**
     * A string used for a check for the OpenButtonListener and
     * CloseButtonListener.
     */
    private static final String JPG = ".jpg";

    /**
     * A string used for a check for the OpenButtonListener and
     * CloseButtonListener.
     */
    private static final String JPEG = ".jpeg";

    /**
     * A string used for a check for the OpenButtonListener and
     * CloseButtonListener.
     */
    private static final String PNG = ".png";

    /**
     * A list of the filter buttons to be used.
     */
    private final List<JButton> myFilterButtons = new ArrayList<>();

    /**
     * The top panel which contains all of the filter buttons.
     */
    private final JPanel myTopPanel = new JPanel();

    /**
     * The image selected by myOpenButton.
     */
    private PixelImage myPixelImage;

    /**
     * A JLabel to convert myPixelImage into an icon that can be added to the
     * GUI.
     */
    private final JLabel myLabel = new JLabel("", SwingConstants.CENTER);

    /**
     * The button that will load a selected image.
     */
    private final JButton myOpenButton = new JButton("Open...");

    /**
     * The button that will save the current image.
     */
    private final JButton mySaveButton = new JButton("Save As...");

    /**
     * The button that removes the image from the GUI.
     */
    private final JButton myCloseButton = new JButton("Close Image");

    /**
     * A JFileChooser for selecting images through myLoadButton.
     */
    private final JFileChooser myChooser;

    /**
     * The GUI's initial dimension.
     */
    private Dimension myInitialDimension;

    /**
     * Initializes the JFrame.
     */
    public SnapShopGUI() {
        super("TCSS 305 SnapShop");
        myChooser = new JFileChooser();
    }

    /**
     * Creates a filter button from a given Filter subclass.
     * 
     * @param theFilterType the Filter subclass being used
     * @return a button for the Filter subclass
     */
    private JButton createFilterButton(final Filter theFilterType) {
        final JButton button = new JButton(theFilterType.getDescription());
        button.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent theEvent) {
                theFilterType.filter(myPixelImage);
                myLabel.setIcon(new ImageIcon(myPixelImage));
            }
        });
        return button;
    }

    /**
     * Sets up the components found inside this JFrame.
     */
    public void setUpComponents() {
        final List<Filter> filters = new ArrayList<>();
        filters.add(new EdgeDetectFilter());
        filters.add(new EdgeHighlightFilter());
        filters.add(new FlipHorizontalFilter());
        filters.add(new FlipVerticalFilter());
        filters.add(new GrayscaleFilter());
        filters.add(new SharpenFilter());
        filters.add(new SoftenFilter());
        for (final Filter aFilter : filters) {
            myFilterButtons.add(createFilterButton(aFilter));
        }
        setLayout(new BorderLayout());
        for (final JButton b : myFilterButtons) {
            myTopPanel.add(b);
            b.setEnabled(false);
        }

        this.add(myTopPanel, BorderLayout.NORTH);
        myChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        myOpenButton.addActionListener(new OpenButtonListener());
        mySaveButton.addActionListener(new SaveButtonListener());
        myCloseButton.addActionListener(new CloseButtonListener());

        final JPanel bottomPanel = new JPanel(); // Panel with the Open, Save,
                                                 // and Close buttons
        bottomPanel.add(myOpenButton);
        mySaveButton.setEnabled(false);
        bottomPanel.add(mySaveButton);
        myCloseButton.setEnabled(false);
        bottomPanel.add(myCloseButton);

        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Starts the GUI.
     */
    public void start() {
        this.setUpComponents();
        this.pack();
        myInitialDimension = this.getSize();
        this.setMinimumSize(this.getSize());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * A listener for myOpenButton.
     * 
     * @author pcruz95
     */
    final class OpenButtonListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            final String originalPath = myChooser.getCurrentDirectory().getAbsolutePath();
            myChooser.setDialogTitle("Open");
            int returnVal = myChooser.showOpenDialog(null);

            if (returnVal == JFileChooser.CANCEL_OPTION) {
                myChooser.setCurrentDirectory(new File(originalPath));
            }

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                boolean isImage = false;
                try {
                    final String fileName = myChooser.getSelectedFile().
                                    getAbsolutePath().toLowerCase();
                    isImage = fileName.endsWith(GIF) || fileName.endsWith(JPG)
                              || fileName.endsWith(PNG) || fileName.endsWith(JPEG);
                    if (!isImage) {
                        throw new IOException("Not an image");
                    }
                    myPixelImage = PixelImage.load(myChooser.getSelectedFile());
                } catch (final IOException e) {
                    returnVal = JFileChooser.ERROR_OPTION;
                    JOptionPane.showMessageDialog(null,
                                                  "The selected file did not contain an image!"
                                                  , "Error!", JOptionPane.ERROR_MESSAGE);
                    myChooser.setCurrentDirectory(new File(originalPath));
                }

                if (isImage) {
                    myLabel.setIcon(new ImageIcon(myPixelImage));
                    add(myLabel, BorderLayout.CENTER);
                    pack();
                    setMinimumSize(getSize());
                    setLocationRelativeTo(null);
                    for (final Component button : myTopPanel.getComponents()) {
                        button.setEnabled(true);
                    }
                    mySaveButton.setEnabled(true);
                    myCloseButton.setEnabled(true);
                }
            }
        }
    }

    /**
     * A listener for mySaveButton.
     * 
     * @author pcruz95
     *
     */
    final class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            final String originalPath = myChooser.getCurrentDirectory().getAbsolutePath();
            myChooser.setDialogTitle("Save As");
            final int returnVal = myChooser.showSaveDialog(null);

            if (returnVal == JFileChooser.CANCEL_OPTION) {
                myChooser.setCurrentDirectory(new File(originalPath));
            }

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                boolean isImage = false;
                try {
                    final String fileName = myChooser.getSelectedFile().
                                    getAbsolutePath().toLowerCase();
                    
                    isImage = fileName.endsWith(GIF) || fileName.endsWith(JPG)
                              || fileName.endsWith(PNG) || fileName.endsWith(JPEG);
                    if (!isImage) {
                        throw new IOException("Not an image oh no");
                    }
                    myPixelImage.save(myChooser.getSelectedFile());
                    
                } catch (final IOException e) {
                    new JOptionPane();
                    JOptionPane.showMessageDialog(null,
                                                  "The file cannot be resolved to an image!",
                                                  "Error!!", JOptionPane.ERROR_MESSAGE);
                    myChooser.setCurrentDirectory(new File(originalPath));
                }
            }
        }
    }

    /**
     * A listener for myCloseButton.
     * 
     * @author prcruz95
     *
     */
    final class CloseButtonListener implements ActionListener {
        // Close

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            remove(myLabel);
            validate();
            repaint();
            setMinimumSize(myInitialDimension);
            pack();
            setLocationRelativeTo(null);

            for (final Component button : myTopPanel.getComponents()) {
                button.setEnabled(false);
            }
            mySaveButton.setEnabled(false);
            myCloseButton.setEnabled(false);
        }

    }
}
