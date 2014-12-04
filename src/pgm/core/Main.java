package pgm.core;

import pgm.gui.GUI;
import pgm.gui.ImageGUI;

public class Main {
    public static void main(String[] args) {
        // Menu GUI and Image GUI should be distinct, like GIMP

        GUI menus = new GUI();
        menus.setVisible(true);
        menus.setResizable(false);
    }

    public static void openPGM(String filepath) {
        PGM pgm = null;
        try {
            Loader loader = new Loader(filepath);
            pgm = loader.loadPGM();
        } catch (Exception e) {
            System.out.println(e);
        }

        if (pgm != null) {
            ImageGUI imgDisplay = new ImageGUI(pgm, filepath);
            imgDisplay.setVisible(true);
            imgDisplay.setResizable(false);
        }
    }
}
