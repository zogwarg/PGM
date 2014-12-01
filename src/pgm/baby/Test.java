package pgm.baby;

/**
 * Created by Thomas on 12/1/2014.
 */
public class Test {
    public static void main(String[] args) {
        Loader loader = new Loader("feep.pgm");
        GUI gui = new GUI(640, 320);
    }
}
