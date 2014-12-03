package pgm.core;

/**
 * Created by Thomas on 12/1/2014.
 */
public class Test {
    public static void main(String[] args) {

        PGM pgm = null;
        String name = "obama";
        try {
            Loader loader = new Loader("pgm/" + name + ".pgm");
            System.out.println("Loading file into PGM object");
            pgm = loader.loadPGM();
        } catch (Exception e) {
            System.out.println(e);
        }

        if (pgm != null) {
            int[] values = new int[] {50, 100, 250, 500,1000};
            for (int value : values) {
                pgm.boxResize(value, value).save("pgm_out/" + name + ".output." + value + ".box.pgm");
                pgm.nearestNeighborResize(value,value).save("pgm_out/" + name + ".output." + value + ".nnr.pgm");
            }

        }

        System.out.println("Done.");
    }
}
