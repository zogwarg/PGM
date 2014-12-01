package pgm.baby;

/**
 * Created by loic on 01/12/14.
 */
public class PGM {
    private int width;
    private int height;
    private int maxVal;
    private int[][] pixelValues;

    public PGM(int width, int height, int maxVal, int[][] pixelValues) {
        this.width = width;
        this.height = height;
        this.maxVal = maxVal;
        this.pixelValues = pixelValues;
    }

    public int[] histogram() {
        int[] hist = new int[this.maxVal];
        for (int[] row : pixelValues) {
            for(int pixel : row) {
                hist[pixel]++;
            }
        }
        return hist;
    }

    public PGM threshold(int thresh) {
        int[][] newPixelValues = new int[this.height][this.width];
        for (int i = 0; i < this.height; i++) {
            for(int j=0; j< this.width; j++) {
                // todo > / >=
                if (this.pixelValues[i][j] > thresh) {
                    newPixelValues[i][j] = 1;
                }
            }
        }
        return new PGM(this.width, this.height, 1, newPixelValues);
    }

    public PGM minus(PGM that) {
        if (this.height != that.height || this.width != that.width || this.maxVal != that.maxVal) {
            throw new UnsupportedOperationException("image parameters must match");
        }
        
    }
}
