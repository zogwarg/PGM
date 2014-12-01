package pgm.baby;

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

    private PGM actualPGM;

    // Menu
    private JMenuBar jMenuBar;
    private JMenu jMenuFile;
    private JMenuItem jFileOpen;
    private JMenuItem jFileSave;
    private JMenuItem jFileExit;
    private JMenu jMenuFilter;
    private JMenuItem jFilterHistogram;
    private JMenuItem jFilterThreshold;
    private JMenuItem jFilterThresholdMean;
    private JMenuItem jFilterThresholdMedian;
    private JMenu jMenuAbout;
    private JMenuItem jAboutInfo;

    /**
     * Create new windows without any PGM file
     * @param w
     * @param h
     */
    GUI(int w, int h) {
        this.height = h;
        this.width = w;
        this.actualPGM = null;

        this.initWindow();
        this.initMenu();
        pack();
    }

    /**
     * Open window with PGM file and options
     * @param openedPGM
     */
    GUI(PGM openedPGM) {
        this.actualPGM = openedPGM;

        this.height = SIZE_OF_PIXEL * actualPGM.getHeight();
        this.width = SIZE_OF_PIXEL * actualPGM.getWidth();

        this.initWindow();
        this.initMenu();
        pack();
    }

    private void initWindow() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Super PGM Bros Java Edition");
        setPreferredSize(new java.awt.Dimension(width, height));
    }

    private void initMenu() {
        // JMenuBar
        jMenuBar = new javax.swing.JMenuBar();
        jMenuBar.setMinimumSize(new java.awt.Dimension(320, 22));
        jMenuBar.setSize(new java.awt.Dimension(320, 20));

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
                        // TODO handle opened file
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Save
        jFileSave = new JMenuItem("Save");
        jFileSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    JFileChooser saveFile = new JFileChooser(this.getClass().getClassLoader().getResource("").getPath());
                    saveFile.addChoosableFileFilter(new FileNameExtensionFilter(".pgm", "pgm"));
                    saveFile.setAcceptAllFileFilterUsed(false);
                    if (saveFile.showSaveDialog(GUI.this) == JFileChooser.APPROVE_OPTION) {
                        // TODO Handle saving file
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
                // TODO exit the program
            }
        });

        // Menu File
        jMenuFilter = new JMenu("Apply filter");

        // Histogram
        jFilterHistogram = new JMenuItem("Histogram");
        jFilterHistogram.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO apply and open histogram
            }
        });

        // Threshold
        jFilterThreshold = new JMenuItem("Threshold");
        jFilterThreshold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO ask for value and apply and open Treshsold
            }
        });

        // Mean Threshold
        jFilterThresholdMean = new JMenuItem("Mean Threshold");
        jFilterThresholdMean.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO apply and open Mean Treshsold
            }
        });

        // Median Threshold
        jFilterThresholdMedian = new JMenuItem("Median Threshold");
        jFilterThresholdMedian.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO apply and open Median Treshsold
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
        jMenuFile.add(jFileSave);
        jMenuFile.add(jFileExit);

        // Adding items to filters menu
        jMenuFilter.add(jFilterHistogram);
        jMenuFilter.add(jFilterThreshold);
        jMenuFilter.add(jFilterThresholdMean);
        jMenuFilter.add(jFilterThresholdMedian);

        // Adding items to about menu
        jMenuAbout.add(jAboutInfo);

        // Adding Menus to Bar
        jMenuBar.add(jMenuFile);
        jMenuBar.add(jMenuFilter);
        jMenuBar.add(jMenuAbout);

        // Ajout du menu a la JFrame
        setJMenuBar(jMenuBar);
    }
}
