package pgm.core;

import pgm.gui.GUI;

public class Main {
    public static void main(String[] args) {
        // Menu GUI and Image GUI should be distinct, like GIMP

        GUI menus = new GUI();
        menus.setVisible(true);
        menus.setResizable(false);
    }
}
