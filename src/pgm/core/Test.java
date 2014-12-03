package pgm.core;

/**
 * Created by Thomas on 12/1/2014.
 */
public class Test {
    public static void main(String[] args) {

        PGM pgm = null;
        try {
            System.out.println("Opening file lena.ascii.pgm Test");
            Loader loader = new Loader("lena.ascii.pgm");
            System.out.println("Loading file into PGM object");
            pgm = loader.loadPGM();
        } catch (Exception e) {
            System.out.println(e);
        }

        if (pgm != null) {
            System.out.println("Resizing with nearest neighbor and saving as lena.1000.pgm");
            pgm.nearestNeighborResize(1000,1000).save("lena.1000.pgm");
        }

        System.out.println("Done.");

        //GUI gui = new GUI(640, 320);
        //gui.setVisible(true);

    }
}
