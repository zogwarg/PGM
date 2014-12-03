package pgm.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Thomas on 12/1/2014.
 * Loader Class
 * Used to open a file, parse the data and fill a PGM object with said data
 */
public class Loader {
    private List<String> tokens;

    /**
     * Constructor
     * Does a pre-parsing task by stripping comments and putting all data on one line
     *
     * @param path the path to the pgm file to be read
     * @throws IOException if unable to read the file or some lines of the file
     */
    public Loader(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        tokens = new ArrayList<String>();

        String line = null;
        while ((line = reader.readLine()) != null) {
            // Remove comments
            line = line.split("#")[0];
            tokens.addAll(Arrays.asList(line.split("\\s+")));
        }
        tokens.remove("");
    }


    /**
     * Creates a new PGM object, by parsing loaded data.
     *
     * @return PGMobject
     * @throws pgm.core.InvalidDataException , if the data is invalid
     */
    public PGM loadPGM() throws InvalidDataException {
        if (!tokens.get(0).equals("P2")) throw new InvalidDataException("There are errors in the format of the P2 PGM");
        int width = Integer.parseInt(tokens.get(1));
        int height = Integer.parseInt(tokens.get(2));
        int maxVal = Integer.parseInt(tokens.get(3));

        if(maxVal<0 || maxVal>255) throw new InvalidDataException("Max Value should be between 0 and 255");

        if(height<0 || width <0) throw new InvalidDataException("Height and width should be positive");

        int size = tokens.size();
        if(size - 4 != width * height) throw new InvalidDataException("Incorrect number of pixel values");

        int[][] pixelValues= new int[height][width];

        int i = 0,j = 0;
        for (String token: tokens.subList(4,size-1)) {
            pixelValues[i][j] = Integer.parseInt(token);
            j++;
            if (j >= width) {
                j=0;
                i++;
            }
        }
        return new PGM(width,height,maxVal,pixelValues);
    }
}
