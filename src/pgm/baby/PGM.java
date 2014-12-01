package pgm.baby;

import java.util.ArrayList;
import java.util.Comparator;

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
        // index of this table goes from 0 to maxVal
        int[] hist = new int[this.maxVal + 1];
        for (int[] row : pixelValues) {
            for(int pixel : row) {
                hist[pixel]++;
            }
        }
        return hist;
    }

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

    public int meanValue() {
        int sum = 0;
        for (int[] row : pixelValues) {
            for (int pixel : row) {
                sum += pixel;
            }
        }
        return Math.floorDiv(sum, width * height);
    }

    public int medianValue() {
        ArrayList<Integer> pixelList = new ArrayList<Integer>();
        for (int[] row : pixelValues) {
            for(int pixel : row) {
                pixelList.add(new Integer(pixel));
            }
        }
        pixelList.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        return pixelList.get(Math.floorDiv(pixelList.size(),2)).intValue();
    }
}
