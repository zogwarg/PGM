package pgm.gui;

import javax.swing.*;

/**
 * Created by loic on 05/12/14.
 */
public class HeightWidthPopup extends JPanel {
    JTextField heightField;
    JTextField widthField;

    public HeightWidthPopup() {
        super();
        widthField = new JTextField(5);
        heightField = new JTextField(5);
        add(new JLabel("width:"));
        add(widthField);
        add(Box.createHorizontalStrut(15)); // a spacer
        add(new JLabel("height:"));
        add(heightField);
    }

    public JTextField getHeightField() {
        return heightField;
    }

    public JTextField getWidthField() {
        return widthField;
    }
}
