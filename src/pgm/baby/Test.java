package pgm.baby;

/**
 * Created by Thomas on 12/1/2014.
 */
public class Test {
    public static void main(String[] args) {
        GUI gui = new GUI(640, 320);
        PGM pgm = null;
        try {
            System.out.println("Opening file obama.pgm");
            Loader loader = new Loader("obama.pgm");
            System.out.println("Loading file into PGM object");
            pgm = loader.loadPGM();
        } catch (Exception e) {
            System.out.println(e);
        }

        if (pgm != null) {
            System.out.println("Saving Histogram as PGM");
            pgm.histogram().save("histogram.pgm");
        }

        System.out.println("Done.");
    }
}
