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
    private final static int MIN_WIDTH = 200;
    private final static int MIN_HEIGHT = 100;

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

    private JMenu jMenuTreatment;
    private JMenuItem jTreatmentHistogram;
    private JMenuItem jTreatmentResizeNearest;
    private JMenuItem jTreatmentResizeBox;
    private JMenuItem jTreatmentResizeBilinear;

    private JMenu jMenuFilter;
    private JMenuItem jFilterInverse;
    private JMenuItem jFilterThreshold;
    private JMenuItem jFilterThresholdMean;
    private JMenuItem jFilterThresholdMedian;
    private JMenuItem jFilterPosterize;

    private JMenu jMenuAbout;
    private JMenuItem jAboutInfo;

    /**
     * Constructor for opening result
     * @param image
     */
    public ImageGUI(PGM image, String filename) {
        width = image.getWidth();
        height = image.getHeight()+43;

        if (width < MIN_WIDTH) {
            width = MIN_WIDTH;
        }
        if (height < MIN_HEIGHT) {
            height = MIN_HEIGHT;
        }

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
        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));

        setResizable(true);
        setVisible(true);
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

        // Menus treatment and filter
        jMenuTreatment = new JMenu("Treatments");
        jMenuFilter = new JMenu("Filters");

        // Histogram
        jTreatmentHistogram = new JMenuItem("Histogram");
        jTreatmentHistogram.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PGM histogram = image.histogram();
                ImageGUI histoGUI = new ImageGUI(histogram, "histogram");
                histoGUI.setResizable(false);
                histoGUI.setVisible(true);
            }
        });

        // Resize
        jTreatmentResizeNearest = new JMenuItem("Resize (nearest)");
        jTreatmentResizeNearest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO ask for value and apply and open Treshsold
            }
        });

        jTreatmentResizeBox = new JMenuItem("Resize (box)");
        jTreatmentResizeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO ask for value and apply and open Treshsold
            }
        });

        jTreatmentResizeBilinear = new JMenuItem("Resize (bilinear)");
        jTreatmentResizeBilinear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO ask for value and apply and open Treshsold
            }
        });

        // Inverse
        jFilterInverse = new JMenuItem("Inverse colors");
        jFilterInverse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = image.medianValue();
                PGM out = image.inverse();
                ImageGUI outGUI = new ImageGUI(out, "Inverse");
                outGUI.setResizable(false);
                outGUI.setVisible(true);
            }
        });

        // Posterize
        jFilterPosterize = new JMenuItem("Posterize");
        jFilterPosterize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO ask for value and apply and open Posterize
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
                int value = image.meanValue();
                PGM out = image.threshold(value);
                ImageGUI outGUI = new ImageGUI(out, "Mean Threshold");
                outGUI.setResizable(false);
                outGUI.setVisible(true);
            }
        });

        // Median Threshold
        jFilterThresholdMedian = new JMenuItem("Median Threshold");
        jFilterThresholdMedian.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = image.medianValue();
                PGM out = image.threshold(value);
                ImageGUI outGUI = new ImageGUI(out, "Median Threshold");
                outGUI.setResizable(false);
                outGUI.setVisible(true);
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

        // Adding items to treatment menu
        jMenuTreatment.add(jTreatmentHistogram);
        jMenuTreatment.add(jTreatmentResizeNearest);
        jMenuTreatment.add(jTreatmentResizeBox);
        jMenuTreatment.add(jTreatmentResizeBilinear);

        // Adding items to filters menu
        jMenuFilter.add(jFilterInverse);
        jMenuFilter.add(jFilterPosterize);
        jMenuFilter.add(jFilterThreshold);
        jMenuFilter.add(jFilterThresholdMean);
        jMenuFilter.add(jFilterThresholdMedian);

        // Adding items to about menu
        jMenuAbout.add(jAboutInfo);

        // Adding Menus to Bar
        jMenuBar.add(jMenuFile);
        jMenuBar.add(jMenuTreatment);
        jMenuBar.add(jMenuFilter);
        jMenuBar.add(jMenuAbout);

        // Ajout du menu a la JFrame
        setJMenuBar(jMenuBar);
    }

    private void loadImg() {
        canvas = new PGMCanvas(image);
        this.getContentPane().add(canvas);
    }
}
