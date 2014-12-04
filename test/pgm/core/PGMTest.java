package pgm.core;

import junit.framework.TestCase;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Before;
public class PGMTest extends TestCase {

    private PGM pgm;

    @Before
    public void setUp() throws Exception {
        pgm = new PGM(10, 5, 255, new int[][] {
                { 5,  5,  5,  5,  5},
                {15, 15, 15, 15, 15},
                {25, 25, 25, 25, 25},
                {35, 35, 35, 35, 35},
                {45, 45, 45, 45, 45},
                {55, 55, 55, 55, 55},
                {65, 65, 65, 65, 65},
                {75, 75, 75, 75, 75},
                {85, 85, 85, 85, 85},
                {95, 95, 95, 95, 95}
        });
    }


    @Test
    public void testGetHeight() throws Exception {
        assertEquals(10, pgm.getHeight());
    }

    @Test
    public void testGetWidth() throws Exception {
        assertEquals(5, pgm.getWidth());
    }

    @Test
    public void testHistogramValues() throws Exception {
        int [] pgmHistogramValues = new int[256];
        for (int i = 0; i < 10; i++) {
            pgmHistogramValues[10*i + 5] = 5;
        }
        assertArrayEquals(pgmHistogramValues, pgm.histogramValues());
    }

    @Test
    public void testHistogram() throws Exception {
        int [][] pgmHistogramPixels = new int[5][256];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                pgmHistogramPixels[i][10*j + 5]=255;
            }
        }
        PGM pgmHistogram = new PGM(5, 256, 255, pgmHistogramPixels);
        assertEquals(pgmHistogram, pgm.histogram());
    }

    @Test
    public void testThreshold() throws Exception {
        assertEquals(new PGM(10, 5, 255, new int[][] {
            {255, 255, 255, 255, 255},
            {255, 255, 255, 255, 255},
            {255, 255, 255, 255, 255},
            {255, 255, 255, 255, 255},
            {255, 255, 255, 255, 255},
            {255, 255, 255, 255, 255},
            {255, 255, 255, 255, 255},
            {255, 255, 255, 255, 255},
            {255, 255, 255, 255, 255},
            {255, 255, 255, 255, 255}
        }),pgm.threshold(5));
        assertEquals(new PGM(10, 5, 255, new int[][] {
            {  0,   0,   0,   0,   0},
            {255, 255, 255, 255, 255},
            {255, 255, 255, 255, 255},
            {255, 255, 255, 255, 255},
            {255, 255, 255, 255, 255},
            {255, 255, 255, 255, 255},
            {255, 255, 255, 255, 255},
            {255, 255, 255, 255, 255},
            {255, 255, 255, 255, 255},
            {255, 255, 255, 255, 255}
        }),pgm.threshold(6));
    }

    @Test
    public void testPosterize() throws Exception {
        PGM pgmPost1 = new PGM(10, 5, 255, new int[][] {
                {45, 45, 45, 45, 45},
                {45, 45, 45, 45, 45},
                {45, 45, 45, 45, 45},
                {45, 45, 45, 45, 45},
                {45, 45, 45, 45, 45},
                {45, 45, 45, 45, 45},
                {45, 45, 45, 45, 45},
                {45, 45, 45, 45, 45},
                {45, 45, 45, 45, 45},
                {45, 45, 45, 45, 45},
        });
        PGM pgmPost2 = new PGM(10, 5, 255, new int[][] {
                {25, 25, 25, 25, 25},
                {25, 25, 25, 25, 25},
                {25, 25, 25, 25, 25},
                {25, 25, 25, 25, 25},
                {25, 25, 25, 25, 25},
                {75, 75, 75, 75, 75},
                {75, 75, 75, 75, 75},
                {75, 75, 75, 75, 75},
                {75, 75, 75, 75, 75},
                {75, 75, 75, 75, 75}
        });
        PGM pgmPost3 = new PGM(10, 5, 255, new int[][] {
                {15, 15, 15, 15, 15},
                {15, 15, 15, 15, 15},
                {15, 15, 15, 15, 15},
                {15, 15, 15, 15, 15},
                {45, 45, 45, 45, 45},
                {45, 45, 45, 45, 45},
                {45, 45, 45, 45, 45},
                {75, 75, 75, 75, 75},
                {75, 75, 75, 75, 75},
                {75, 75, 75, 75, 75},
        });

        assertEquals(pgmPost1,pgm.posterize(1));
        assertEquals(pgmPost2,pgm.posterize(2));
        assertEquals(pgmPost3,pgm.posterize(3));
        assertEquals(pgm, pgm.posterize(255));
        assertEquals(pgm,pgm.posterize(10));

    }

    @Test
    public void testMinus() throws Exception {
        assertEquals(new PGM(10, 5, 255, new int[][] {
                {  5,   5,   5,   5,   5},
                { 15,  15,  15,  15,  15},
                { 25,  25,  25,  25,  25},
                { 35,  35,  35,  35,  35},
                { 45,  45,  45,  45,  45},
                { 55,  55,  55,  55,  55},
                { 65,  65,  65,  65,  65},
                {180, 180, 180, 180, 180},
                {170, 170, 170, 170, 170},
                {160, 160, 160, 160, 160}
        }),pgm.minus(pgm.threshold(70)));
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