package pgm.gui;

import pgm.core.Main;
import pgm.core.PGM;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by thomas on 04/12/14.
 */
public class ImageGUI extends JFrame {
    private int height;
    private int width;
    private PGM image;
    private String filename;
    private PGMCanvas canvas;

    // Menu
    private JMenuBar jMenuBar;
    private JMenu jMenuFile;
    private JMenuItem jFileOpen;
    private JMenuItem jFileSave;
    private JMenuItem jFileClose;
    private JMenuItem jFileExit;
    private JMenu jMenuFilter;
    private JMenuItem jFilterHistogram;
    private JMenuItem jFilterThreshold;
    private JMenuItem jFilterThresholdMean;
    private JMenuItem jFilterThresholdMedian;
    private JMenu jMenuAbout;
    private JMenuItem jAboutInfo;

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
        this.initMenu();
        this.loadImg();
        pack();
    }

    private void initWindow() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(filename);
        setPreferredSize(new java.awt.Dimension(width, height+20));
    }

    private void initMenu() {
        // JMenuBar
        jMenuBar = new javax.swing.JMenuBar();

        // Menu File
        jMenuFile = new JMenu("File");

        // Open
        jFileOpen = new JMenuItem("Open file");
        jFileOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    JFileChooser openFile = new JFileChooser(this.getClass().getClassLoader().getResource("").getPath());
                    openFile.addChoosableFileFilter(new FileNameExtensionFilter(".pgm", "pgm"));
                    openFile.setAcceptAllFileFilterUsed(false);
                    if (openFile.showOpenDialog(ImageGUI.this) == JFileChooser.APPROVE_OPTION) {
                        String file = openFile.getSelectedFile().getCanonicalPath();
                        System.out.println("Opening "+file);
                        Main.openPGM(file);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Save
        jFileSave = new JMenuItem("Save file");
        jFileSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    JFileChooser saveFile = new JFileChooser(this.getClass().getClassLoader().getResource("").getPath());
                    saveFile.addChoosableFileFilter(new FileNameExtensionFilter(".pgm", "pgm"));
                    saveFile.setAcceptAllFileFilterUsed(false);
                    if (saveFile.showSaveDialog(ImageGUI.this) == JFileChooser.APPROVE_OPTION) {
                        String file = saveFile.getSelectedFile().getCanonicalPath();
                        System.out.println("Saving "+file);
                        image.save(file);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Save
        jFileClose = new JMenuItem("Close file");
        jFileClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });

        // Exit
        jFileExit = new JMenuItem("Exit program");
        jFileExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            // Crude but effective
            System.exit(0);
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
                JOptionPane.showMessageDialog(ImageGUI.this, "Thomas BUICK, Thomas DURAND et Loic MOLLET-PADIER\nVersion 0.3.14", "Informations", JOptionPane.PLAIN_MESSAGE);
            }
        });

        // Adding items to file menu
        jMenuFile.add(jFileOpen);
        jMenuFile.add(jFileSave);
        jMenuFile.add(jFileClose);
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

    private void loadImg() {
        // fixme loading img, in PGM canvas
        canvas = new PGMCanvas(image);
        this.getContentPane().add(canvas);
    }
}
