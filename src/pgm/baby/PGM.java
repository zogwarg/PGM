package pgm.baby;

/**
 * Created by loic on 01/12/14.
 */
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
     * Make an histogramm from the current PGM
     * @return number of occurences of each grayscale between 0 and maxVal-1
     */
    public int[] histogram() {
        int[] hist = new int[this.maxVal];
        for (int[] row : pixelValues) {
            for(int pixel : row) {
                hist[pixel]++;
            }
        }
        return hist;
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
}
