package pgm.core;

/**
 * Created by Thomas on 12/1/2014.
 */
public class Test {
    public static void main(String[] args) {

        PGM pgm = null;
        try {
            Loader loader = new Loader("feep.pgm");
            System.out.println("Loading file into PGM object");
            pgm = loader.loadPGM();
        } catch (Exception e) {
            System.out.println(e);
        }

        if (pgm != null) {
            pgm.save("feep.copy.pgm");
        }

        System.out.println("Done.");
    }
}
