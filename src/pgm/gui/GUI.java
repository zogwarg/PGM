package pgm.gui;

import pgm.core.Main;
import pgm.core.PGM;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by thomas on 01/12/14.
 */
public class GUI extends JFrame {
    protected int height;
    protected int width;

    // Menu
    protected JMenuBar jMenuBar;
    protected JMenu jMenuFile;
    protected JMenuItem jFileOpen;
    protected JMenuItem jFileExit;
    protected JMenu jMenuAbout;
    protected JMenuItem jAboutInfo;

    /**
     * Create new windows without any PGM file
     */
    public GUI() {
        this.width = 320;
        this.height = 240;

        this.initMenu();
        // Ajout du menu a la JFrame

        setJMenuBar(jMenuBar);
        this.initWindow();
        pack();
    }

    private void initWindow() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Super PGM Bros Java Edition");
        setPreferredSize(new Dimension(width, height));
        setResizable(false);
        setVisible(true);
    }

    protected void initMenu() {
        // JMenuBar
        jMenuBar = new JMenuBar();

        // Menu File
        jMenuFile = new JMenu("File");

        // Open
        jFileOpen = new JMenuItem("Open");
        jFileOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    JFileChooser openFile = new JFileChooser(".");
                    openFile.addChoosableFileFilter(new FileNameExtensionFilter(".pgm", "pgm"));
                    openFile.setAcceptAllFileFilterUsed(false);
                    if (openFile.showOpenDialog(GUI.this) == JFileChooser.APPROVE_OPTION) {
                        String file = openFile.getSelectedFile().getCanonicalPath();
                        System.out.println(file);
                        dispose(); // We close the actual window
                        ImageGUI.openPGM(file);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Exit
        jFileExit = new JMenuItem("Exit");
        jFileExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crude but effective
                System.exit(0);
            }
        });

        // About menu
        jMenuAbout = new JMenu("About");
        jAboutInfo = new JMenuItem("Informations");
        jAboutInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(GUI.this, "Thomas BUICK, Thomas DURAND et Loic MOLLET-PADIER\nVersion 0.3.14", "Informations", JOptionPane.PLAIN_MESSAGE);
            }
        });

        // Adding items to file menu
        jMenuFile.add(jFileOpen);
        jMenuFile.add(jFileExit);

        // Adding items to about menu
        jMenuAbout.add(jAboutInfo);

        // Adding Menus to Bar
        jMenuBar.add(jMenuFile);
        jMenuBar.add(jMenuAbout);
    }
}
