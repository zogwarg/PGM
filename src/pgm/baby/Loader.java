package pgm.baby;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by Thomas on 12/1/2014.
 */
public class Loader {
    private String path;

    public Loader(String path) {
        this.path = path;
        BufferedReader reader;
        ArrayList<String> lines = new ArrayList();

        try {
            reader = new BufferedReader(new FileReader(path));

            String line = null;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }




    }


}
