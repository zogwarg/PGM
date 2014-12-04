package pgm.gui;

import pgm.core.Main;
import pgm.core.PGM;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by thomas on 01/12/14.
 */
public class GUI extends JFrame {
    private static final int SIZE_OF_PIXEL = 20;

    private int height;
    private int width;

    // Menu
    private JMenuBar jMenuBar;
    private JMenu jMenuFile;
    private JMenuItem jFileOpen;
    private JMenuItem jFileExit;
    private JMenu jMenuAbout;
    private JMenuItem jAboutInfo;

    /**
     * Create new windows without any PGM file
     */
    public GUI() {
        this.height = 43;
        this.width = 320;

        this.initWindow();
        this.initMenu();
        pack();
    }

    private void initWindow() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Super PGM Bros Java Edition");
        setPreferredSize(new java.awt.Dimension(width, height));
    }

    private void initMenu() {
        // JMenuBar
        jMenuBar = new javax.swing.JMenuBar();

        // Menu File
        jMenuFile = new JMenu("File");

        // Open
        jFileOpen = new JMenuItem("Open");
        jFileOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    JFileChooser openFile = new JFileChooser(this.getClass().getClassLoader().getResource("").getPath());
                    openFile.addChoosableFileFilter(new FileNameExtensionFilter(".pgm", "pgm"));
                    openFile.setAcceptAllFileFilterUsed(false);
                    if (openFile.showOpenDialog(GUI.this) == JFileChooser.APPROVE_OPTION) {
                        String file = openFile.getSelectedFile().getCanonicalPath();
                        System.out.println(file);
                        Main.openPGM(file);
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

        // Ajout du menu a la JFrame
        setJMenuBar(jMenuBar);
    }
}
