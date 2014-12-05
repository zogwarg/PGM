package pgm.gui;

import pgm.core.Loader;
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
public class ImageGUI extends GUI {
    private final static int MIN_WIDTH = 200;
    private final static int MIN_HEIGHT = 100;

    private PGM image;
    private String filename;
    private PGMCanvas canvas;

    // Menu
    private JMenuItem jFileSave;
    private JMenuItem jFileClose;

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

    /**
     * Constructor for opening result
     * @param image
     */
    public ImageGUI(PGM image, String filename) {
        this.width = Math.max(image.getWidth(), MIN_WIDTH);
        this.height = Math.max(image.getHeight()+43, MIN_HEIGHT);

        this.image = image;
        this.filename = filename;

        this.initWindow();
        this.initMenu();
        this.loadImg();

        // Ajout du menu a la JFrame
        setJMenuBar(jMenuBar);
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

    protected void initMenu() {
        super.initMenu();

        // JMenuBar
        jMenuBar = new JMenuBar();
        // Save
        jFileSave = new JMenuItem("Save file");
        jFileSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    JFileChooser saveFile = new JFileChooser(".");
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
                HeightWidthPopup myPanel = new HeightWidthPopup();
                while(true) {
                    int width;
                    int height;
                    int result = JOptionPane.showConfirmDialog(null, myPanel,
                            "Please Enter Width and Height Values", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {
                        try {
                            width = Integer.parseInt(myPanel.getWidthField().getText());
                            height = Integer.parseInt(myPanel.getHeightField().getText());
                        } catch (NumberFormatException nfe) {
                            width = 0;
                            height = 0;
                        }
                        // If valid size
                        if (width > 0 && height > 0) {
                            ImageGUI imageGUI = new ImageGUI(image.nearestNeighborResize(height,width), filename+"_nnResize");
                            imageGUI.setResizable(false);
                            imageGUI.setVisible(true);
                            break;
                        }
                    } else if (result == JOptionPane.CANCEL_OPTION) {
                        break;
                    }
                }
            }
        });

        jTreatmentResizeBox = new JMenuItem("Resize (box)");
        jTreatmentResizeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HeightWidthPopup myPanel = new HeightWidthPopup();
                while(true) {
                    int width;
                    int height;
                    int result = JOptionPane.showConfirmDialog(null, myPanel,
                            "Please Enter Width and Height Values", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {
                        try {
                            width = Integer.parseInt(myPanel.getWidthField().getText());
                            height = Integer.parseInt(myPanel.getHeightField().getText());
                        } catch (NumberFormatException nfe) {
                            width = 0;
                            height = 0;
                        }
                        // If valid size
                        if (width > 0 && height > 0) {
                            ImageGUI imageGUI = new ImageGUI(image.boxResize(height, width), filename+"_boxResize");
                            break;
                        }
                    } else if (result == JOptionPane.CANCEL_OPTION) {
                        break;
                    }
                }
            }
        });

        jTreatmentResizeBilinear = new JMenuItem("Resize (bilinear)");
        jTreatmentResizeBilinear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HeightWidthPopup myPanel = new HeightWidthPopup();
                while(true) {
                    int height;
                    int width;
                    int result = JOptionPane.showConfirmDialog(null, myPanel,
                            "Please Enter Width and Height Values", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {
                        try {
                            width = Integer.parseInt(myPanel.getWidthField().getText());
                            height = Integer.parseInt(myPanel.getHeightField().getText());
                        } catch (NumberFormatException nfe) {
                            width = 0;
                            height = 0;
                        }
                        // If valid size
                        if (width > 0 && height > 0) {
                            ImageGUI imageGUI = new ImageGUI(image.bilinearResize(height, width), filename+"_blResize");
                            imageGUI.setResizable(false);
                            imageGUI.setVisible(true);
                            break;
                        }
                    } else if (result == JOptionPane.CANCEL_OPTION) {
                        break;
                    }
                }
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
                JPanel myPanel = new JPanel();
                JTextField sliceField = new JTextField(5);
                myPanel.add(new JLabel("Number of colors:"));
                myPanel.add(sliceField);
                int slices;
                while(true) {
                    int result = JOptionPane.showConfirmDialog(null, myPanel,
                            "Please Enter the number of colors", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {
                        try {
                            slices = Integer.parseInt(sliceField.getText());
                        } catch (NumberFormatException nfe) {
                            slices = 0;
                        }
                        // If valid size
                        if (slices > 0) {
                            ImageGUI imageGUI = new ImageGUI(image.posterize(slices), filename+"_posterize");
                            imageGUI.setResizable(false);
                            imageGUI.setVisible(true);
                            break;
                        }
                    } else if (result == JOptionPane.CANCEL_OPTION) {
                        break;
                    }
                }
            }
        });

        // Threshold
        jFilterThreshold = new JMenuItem("Threshold");
        jFilterThreshold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel myPanel = new JPanel();
                JTextField sliceField = new JTextField(5);
                myPanel.add(new JLabel("Threshold:"));
                myPanel.add(sliceField);
                int threshold;
                while(true) {
                    int result = JOptionPane.showConfirmDialog(null, myPanel,
                            "Please Enter the value of the threshold", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {
                        try {
                            threshold = Integer.parseInt(sliceField.getText());
                        } catch (NumberFormatException nfe) {
                            threshold = 0;
                        }
                        // If valid size
                        if (threshold > 0) {
                            ImageGUI imageGUI = new ImageGUI(image.threshold(threshold), filename + "_threshold");
                            imageGUI.setResizable(false);
                            imageGUI.setVisible(true);
                            break;
                        }
                    } else if (result == JOptionPane.CANCEL_OPTION) {
                        break;
                    }
                }
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
        jMenuFile.add(jFileSave, 1);
        jMenuFile.add(jFileClose, 2);

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


        // Adding Menus to Bar
        jMenuBar.add(jMenuFile);
        jMenuBar.add(jMenuTreatment);
        jMenuBar.add(jMenuFilter);
        jMenuBar.add(jMenuAbout);
    }

    private void loadImg() {
        canvas = new PGMCanvas(image);
        this.getContentPane().add(canvas);
    }

    public static <T> T last(T[] array) {
        return array[array.length - 1];
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
            ImageGUI imgDisplay = new ImageGUI(pgm, last(filepath.split("/")));
        }

    }
}
