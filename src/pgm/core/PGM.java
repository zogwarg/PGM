package pgm.core;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;
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

    public int getPixelValue(int x, int y) {
        return pixelValues[x][y];
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
     * Reduces the number of different colors in an image by looking at the histogram
     * @param colorNum the number of target colors
     * @return
     */
    public PGM posterize(int colorNum) throws InvalidParameterException {
        if ( colorNum<1 || colorNum>maxVal ) throw new InvalidParameterException("The parameter should be between 1 and " + maxVal);
        int countPixel = 0;
        int numPixel = width * height;

        int[] histogram = histogramValues();
        int length = histogram.length;

        int[] valueMap = new int[length];
        int curVal = 0;

        for (int i = 0; i < length; i++) {
            countPixel += histogram[i]; // Adding the Population for I_th color value in Histogram
            valueMap[i] = (maxVal*curVal)/colorNum;

            if (countPixel>=numPixel/colorNum) {
                countPixel -= numPixel/colorNum; // Resetting the count when a population slice is reached
                curVal++;
            }
        }

        int[][] newPixelValues = new int[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                newPixelValues[i][j] = valueMap[pixelValues[i][j]];
            }
        }

        return new PGM(height,width,colorNum,newPixelValues);
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

    /**
     * Create a new image using decimate / box resize
     * @param newHeight height of the new image
     * @param newWidth width of the new image
     * @return the new image
     */
    public PGM boxResize(int newHeight, int newWidth) {
        int[][] newPixelValues = new int[newHeight][newWidth];
        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < newWidth; j++) {
                newPixelValues[i][j] = getValueBox(newHeight, newWidth, i, j);
            }
        }
        return new PGM(newHeight, newWidth, maxVal, newPixelValues);
    }

    /**
     * Returns the value of the pixel of the new image using decimate / box resize
     * @param newHeight height of the new image
     * @param newWidth width of the new image
     * @param row the row of the pixel to be colored
     * @param col the col of the pixel to be colored
     * @return the value of that pixel
     */
    private int getValueBox(int newHeight, int newWidth, int row, int col) {
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
            int sizeI = PGM.getRatio(i, row, height, newHeight);
            for (int j = minCol; j < maxCol; j++) {
                int size = sizeI * PGM.getRatio(j, col, width, newWidth);
                value += pixelValues[i][j] * size;
                area += size;
            }
        }
        return value/area;
    }

    /**
     * Static helper method to help calculate the overlap ratio of two pixels.
     * This calculates the overlap ratio over one coordinate
     * @param oldVal the coordinate in the original image
     * @param newVal the coordinate in the final image
     * @param oldSize the coordinate's max size in the original image
     * @param newSize the coordinate's max size in the final image
     * @return the overlap times newSize
     */
    private static int getRatio(int oldVal, int newVal, int oldSize, int newSize) {
        if (oldVal * newSize < newVal * oldSize) {
            return (newSize - newVal * oldSize + oldVal * newSize);
        } else if (oldVal * newSize > (newVal+1) * oldSize) {
            return (newSize - oldVal * newSize + (newVal+1) * oldSize);
        } else {
            return newSize;
        }
    }

    /**
     * Create a new image using the bi-linear interpolation algorithm
     * @param newHeight height of the new image
     * @param newWidth width of the new image
     * @return the new image
     */
    public PGM bilinearResize(int newHeight, int newWidth) {
        int[][] newPixelValues = new int[newHeight][newWidth];
        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < newWidth; j++) {
                newPixelValues[i][j] = getValueBilinear(newHeight, newWidth, i, j);
            }
        }
        return new PGM(newHeight, newWidth, maxVal, newPixelValues);
    }

    /**
     * Returns the value of the new pixel using bi-linear interpolation
     * @param newHeight height of the new image
     * @param newWidth width of the new image
     * @param row the row of the pixel to be colored
     * @param col the col of the pixel to be colored
     * @return the value of that pixel
     */
    private int getValueBilinear(int newHeight, int newWidth, int row, int col) {
        int i0 = (row * height) / newHeight;
        int j0 = (col * width ) / newWidth;
        long diNewHeight = row * height - i0 * newHeight;
        long djNewWidth  = col * width  - j0 * newWidth;
        long sum = 0;
        // nH * nW * (1-di) * (1-dj)
        sum += (newHeight - diNewHeight) * (newWidth - djNewWidth) * getValue(i0,j0);
        // nH * nW * (1-di) * dj
        sum += (newHeight - diNewHeight) * djNewWidth * getValue(i0,j0 + 1);
        // nH * nW * di * (1-dj)
        sum += diNewHeight * (newWidth - djNewWidth) * getValue(i0+1,j0);
        // nH * nW * di * dj
        sum += diNewHeight * djNewWidth * getValue(i0 + 1,j0 + 1);
        return (int) (sum / (newHeight * newWidth));
    }

    /**
     * Safe accessor for pixelValues
     * @param row the row to be accessed
     * @param col the column to be accessed
     * @return pixelValues[row][col] if exists, 0 otherwise
     */
    private int getValue(int row,int col) {
        if (row >= height || col >= width || row < 0 || col < 0) {
            return 0;
        } else {
            return pixelValues[row][col];
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PGM pgm = (PGM) o;

        if (height != pgm.height) return false;
        if (maxVal != pgm.maxVal) return false;
        if (width != pgm.width) return false;
        int[][] pgmPV = pgm.getPixelValues();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (pixelValues[i][j] != pgmPV[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public int[][] getPixelValues() {
        return pixelValues;
    }
}
