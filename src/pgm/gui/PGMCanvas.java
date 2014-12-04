package pgm.gui;

import pgm.core.PGM;

import java.awt.*;

/**
 * Created by thomas on 04/12/14.
 */
public class PGMCanvas extends Canvas {
    private int height;
    private int width;
    private PGM image;

    public PGMCanvas(PGM image) {
        this.image = image;
        this.height = image.getHeight();
        this.width = image.getWidth();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for(int x = 0; x < WIDTH; x++) {
            for(int y = 0; y < HEIGHT; y++) {
                int grayValue = image.getPixelValue(x, y);
                Color color = new Color(grayValue, grayValue, grayValue);
                g.setColor(color);
                g.drawLine(x, y, x, y);
            }
        }
    }
}
