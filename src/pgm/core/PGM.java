package pgm.core;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PGM {
    private int height;
    private int width;
    private int maxVal;
    private int[][] pixelValues;

    /**
     * Constructor for a PGM
     * @param height number of rows for the matrix
     * @param width number of colomns for the matrix
     * @param maxVal Threshold for white value, give the number of grayscale available
     * @param pixelValues Matrix to initialize a new PGM
     */
    public PGM(int height, int width,  int maxVal, int[][] pixelValues) {
        this.height = height;
        this.width = width;
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
                    newPixelValues[i][j] = maxVal;
                }
            }
        }

        return new PGM(height, width, maxVal, newPixelValues);
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
        return new PGM(height, width, maxVal, newPixelValues);
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
        return new PGM(height, width, maxVal, newPixelValues);
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

    public PGM boxResize(int newHeight, int newWidth) {
        int[][] newPixelValues = new int[newHeight][newWidth];
        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < newWidth; j++) {
                newPixelValues[i][j] = getValue(newHeight, newWidth, i, j);
            }
        }
        return new PGM(newHeight, newWidth, maxVal, newPixelValues);
    }

    private int getValue(int newHeight, int newWidth, int row, int col) {
        // Integer division, equivalent to floor
        int minRow = (row * height) / newHeight;
        int minCol = (col * width ) / newWidth;
        // Ceil of ((row + 1) * height) / newHeight
        int maxRow = ((newHeight - 1) + ((row + 1) * height)) / newHeight;
        // Ceil of ((col + 1) * width) / width
        int maxCol = ((newWidth  - 1) + ((col + 1) * width )) / newWidth;
        int value = 0;
        int area = 0;
        for (int i = minRow; i < maxRow; i++){
            int sizeI = getRatio(i,row,height,newHeight);
            for (int j = minCol; j < maxCol; j++) {
                int size = sizeI * getRatio(j,col,width,newWidth);
                value += pixelValues[i][j] * size;
                area += size;
            }
        }
        return value/area;
    }

    // todo javadoc
    // returns a size object of overlab between two pixels. value between 0 and newSize
    private int getRatio(int oldVal, int newVal, int oldSize, int newSize) {
        if (oldVal * newSize < newVal * oldSize) {
            return (newSize - newVal * oldSize + oldVal * newSize);
        } else if (oldVal * newSize > (newVal+1) * oldSize) {
            return (newSize - oldVal * newSize + (newVal+1) * oldSize);
        } else {
            return newSize;
        }
    }

    /**
     * Simplest and fastest resize, but image quality suffers
     * @param newWidth width of the new image
     * @param newHeight height of the new image
     * @return the new image
     */
    public PGM nearestNeighborResize(int newHeight, int newWidth) {
        int[][] newPixelValues = new int[newHeight][newWidth];
        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < newWidth; j++) {
                newPixelValues[i][j] = pixelValues[(i * height) / newHeight][(j * width) / newWidth];
            }
        }
        return new PGM(newHeight, newWidth, maxVal, newPixelValues);
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
            bufferedWriter.write("P2\n# Generated with super short generator\n"+width + " " + height + "\n" + maxVal + "\n");
            int lineLength = 0;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    String value = Integer.toString(pixelValues[i][j]);
                    if (lineLength + value.length() + 1 > 70) {
                        bufferedWriter.write("\n");
                        lineLength = value.length() + 1;
                    } else {
                        lineLength += value.length() +1;
                    }
                    bufferedWriter.write(value + " ");
                }
            }
            bufferedWriter.write("\n");

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
}
