package pgm.core;

import junit.framework.UnitTest;
import org.junit.*;
import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.Test;
public class PGMTest extends UnitTest {

    PGM checkerboard;
    @Before
    public void createPGMs() {
        checkerboard = new PGM(2, 4, 255, new int[][] {{255, 0, 255, 0},{0, 255, 0, 255}});
    }


    @Test
    public void testGetHeight() throws Exception {
        assertEquals(2, checkerboard.getHeight());
    }

    @Test
    public void testGetWidth() throws Exception {
        assertEquals(4, checkerboard.getWidth());
    }

    @Test
    public void testHistogramValues() throws Exception {
        int [] checkerHistogramValues = new int[256];
        checkerHistogramValues[0] = 4;
        checkerHistogramValues[255] = 4;
        assertArrayEquals(checkerHistogramValues, checkerboard.histogramValues());
    }

    @Test
    public void testHistogram() throws Exception {
        int [][] checkerboardHistogramPixels = new int[4][256];
        for (int j = 0; j < 4; j++) {
            checkerboardHistogramPixels[0][j] = 255;
            checkerboardHistogramPixels[255][j] = 255;
        }
        PGM checkerboardHistogram = new PGM(4, 256, 255, checkerboardHistogramPixels);
        assertEquals(checkerboardHistogram, checkerboard.histogram());
    }

    @Test
    public void testThreshold() throws Exception {

    }

    @Test
    public void testPosterize() throws Exception {

    }

    @Test
    public void testMinus() throws Exception {

    }

    @Test
    public void testMeanValue() throws Exception {

    }

    @Test
    public void testMedianValue() throws Exception {

    }

    @Test
    public void testBoxResize() throws Exception {

    }

    @Test
    public void testBilinearResize() throws Exception {

    }

    @Test
    public void testNearestNeighborResize() throws Exception {

    }

    @Test
    public void testSave() throws Exception {

    }
}