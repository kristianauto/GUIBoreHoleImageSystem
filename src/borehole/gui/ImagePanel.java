package borehole.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * This class is an extended version of JPanel. Added methods required to
 * display BufferedImage.
 *
 * @author Kristian Homdrom
 */
public class ImagePanel extends JPanel {

    private BufferedImage image;
    private int width, height;

    /**
     * Constructor to create the Sheet object
     *
     * @param width The width of the sheet
     * @param height The height of the sheet
     */
    public ImagePanel(int width, int height) {
        setSize(width, height);
    }

    /**
     * Updates the sheet to display the most recent image
     *
     * @param img Image to display in the component
     */
    public void paintSheet(BufferedImage img) {
        image = null;
        image = img;
        repaint();
    }

    /**
     * Uses paintComponent method of its super class and makes the component
     * compatible with bufferedImage
     *
     * @param g A graphics context onto which a bufferedImage can be drawn
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
