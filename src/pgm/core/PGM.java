package pgm.core;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PGM {
    private int width;
    private int height;
    private int maxVal;
    private int[][] pixelValues;

    /**
     * Constructor for a PGM
     * @param width number of colomns for the matrix
     * @param height number of rows for the matrix
     * @param maxVal Threshold for white value, give the number of grayscale available
     * @param pixelValues Matrix to initialize a new PGM
     */
    public PGM(int width, int height, int maxVal, int[][] pixelValues) {
        this.width = width;
        this.height = height;
        this.maxVal = maxVal;
        this.pixelValues = pixelValues;
    }

    /**
     * Height getter
     * @return height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Width getter
     * @return width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Get an histogram values from the current PGM
     * @return number of occurences of each grayscale between 0 and maxVal
     */
    public int[] histogramValues() {
        int[] hist = new int[this.maxVal+1];
        for (int[] row : pixelValues) {
            for(int pixel : row) {
                hist[pixel]++;
            }
        }
        return hist;
    }

    /**
     * Getting the histogram in a PGM form
     * @return PGM with the histogram
     */
    public PGM histogram() {
        int[] values = histogramValues();
        int height = 1;
        for (int v: values) { // Looking for the maximum value in histogram
            if (v > height) {
                height = v;
            }
        }
        int width = values.length; // The width of the histogram is the number of grayscales

        int[][] newPixelValues = new int[height][width];
        for (int j = 0; j < width; j++) {
            for (int i = 0; i < height; i++) {
                if (i >= height-values[j]) {
                    newPixelValues[i][j] = 1;
                }
            }
        }

        return new PGM(width, height, 1, newPixelValues);
    }

    /**
     * Apply a threshold filter on a opened PGM file
     * @param thresh affect the threshold for comparison
     * @return black and white PGM file thresholded
     */
    public PGM threshold(int thresh) {
        int[][] newPixelValues = new int[height][width];
        for (int i = 0; i < height; i++) {
            for(int j=0; j< width; j++) {
                if (pixelValues[i][j] >= thresh) {
                    newPixelValues[i][j] = maxVal;
                }
            }
        }
        return new PGM(width, height, maxVal, newPixelValues);
    }

    /**
     * Allow minus comparison between two PGM files with the same size and maxVal
     * @param that other PGM file to compare with the first one
     * @return PGM file to show minus differences
     */
    public PGM minus(PGM that) {
        if (height != that.height || width != that.width || maxVal != that.maxVal) {
            throw new UnsupportedOperationException("image parameters must match");
        }
        int[][] newPixelValues = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                newPixelValues[i][j] = Math.abs(pixelValues[i][j]-that.pixelValues[i][j]);
            }
        }
        return new PGM(width, height, maxVal, newPixelValues);
    }

    /**
     * Get the mean value of the pixels of a PGM image
     * @return the mean value
     */
    public int meanValue() {
        int sum = 0;
        for (int[] row : pixelValues) {
            for (int pixel : row) {
                sum += pixel;
            }
        }
        return Math.floorDiv(sum, width * height);
    }

    /**
     * Get the median value of the pixels of a PGM image
     * @return the median value
     */
    public int medianValue() {
        List<Integer> pixelList = getPixelList();
        Collections.sort(pixelList);
        return pixelList.get(Math.floorDiv(pixelList.size(), 2));
    }

    /**
     * Get all the pixels of the image as a flat List
     * @return the list of all elements in the image
     */
    private List<Integer> getPixelList() {
        List<Integer> pixelList = new ArrayList<Integer>();
        for (int[] row : pixelValues) {
            for(int pixel : row) {
                pixelList.add(pixel);
            }
        }
        return pixelList;
    }

    public PGM resize(int newWidth, int newHeight) {
        int[][] newPixelValues = new int[newHeight][newWidth];
        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < newWidth; j++) {
                int pixelValue = 0;
                for (int k = 0; k < height; k++) {
                    for (int l = 0; l < width; l++) {
                        pixelValue += pixelValues[Math.floorDiv(height*i + k, newHeight)][Math.floorDiv(width * j + l, newWidth)];
                    }
                }
                newPixelValues[i][j] = Math.floorDiv(pixelValue, height * width);
            }
        }
        return new PGM(newWidth, newHeight, maxVal, newPixelValues);
    }

    /**
     * Allow to write PGM in a file
     * @param filename filename and path of the saved file
     */
    public void save(String filename) {
        BufferedWriter bufferedWriter = null;
        try{
            // BufferedWriter Creation
            bufferedWriter = new BufferedWriter(new FileWriter(filename));

            // we write in file
            bufferedWriter.write(this.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Give information of actual PGM in text format
     * @return String in PGM Format
     */
    @Override
    public String toString() {
        int charWritten = 0;

        String out = "P2\n";
        out += "# Generated with our super program\n";
        out += Integer.toString(width) + " " + Integer.toString(height) + "\n";
        out += Integer.toString(maxVal) + "\n";

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                // No more than 70 character per line
                charWritten += Integer.toString(pixelValues[i][j]).length()+1;
                if (charWritten > 70) {
                    charWritten = Integer.toString(pixelValues[i][j]).length()+1;
                    out += "\n";
                }

                out += Integer.toString(pixelValues[i][j]) + " ";
            }
        }

        return out;
    }
}
