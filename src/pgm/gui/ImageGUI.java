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
    private String filename;
    private PGMCanvas canvas;

    /**
     * Constructor for opening result
     * @param image
     */
    public ImageGUI(PGM image, String filename) {
        this.height = image.getHeight();
        this.width = image.getWidth();
        this.image = image;
        this.filename = filename;

        this.initWindow();
        this.loadImg();
        pack();
    }

    private void initWindow() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(filename);
        setPreferredSize(new java.awt.Dimension(width, height));
    }

    private void loadImg() {
        // fixme loading img, in PGM canvas
        canvas = new PGMCanvas(image);
        this.getContentPane().add(canvas);
    }
}
