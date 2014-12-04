package pgm.gui;

import pgm.core.PGM;

import javax.swing.*;

/**
 * Created by thomas on 04/12/14.
 */
public class ImageGUI extends JFrame {
    private int height;
    private int width;
    private PGM image;

    /**
     * Constructor for opening result
     * @param image
     */
    public ImageGUI(PGM image) {
        this.height = image.getHeight();
        this.width = image.getWidth();
        this.image = image;

        this.initWindow();
        this.loadImg();
        pack();
    }

    private void initWindow() {
        // Todo create window
    }

    private void loadImg() {
        // Todo sketch pgm img
    }
}
