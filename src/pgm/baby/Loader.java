package pgm.baby;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by Thomas on 12/1/2014.
 */
public class Loader {
    private String path;
    private ArrayList<String> lines;
    private String preParsedLine;

    public Loader(String path) {
        this.path = path;
        BufferedReader reader = new BufferedReader(new FileReader(path));;

        lines = new ArrayList();
        Pattern comment = Pattern.compile("#.*$");
        preParsedLine = "";

        String line = null;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
            preParsedLine+=comment.matcher(line).replaceAll("")+" ";
        }
        
        preParsedLine=Pattern.compile("\\s+").matcher(preParsedLine).replaceAll(" ");
        preParsedLine=Pattern.compile(" $").matcher(preParsedLine).replaceAll("");

        System.out.println(preParsedLine);
    }

    public boolean IsBasicValid() {
        Pattern format = Pattern.compile("^P2 [0-9]+ [0-9]+ [0-9]+ *[ 0-9]*$");
        return format.matcher(preParsedLine).matches();
    }

    public PGM loadPGM() throws InvalidDataException {
        if (!this.IsBasicValid()) throw new InvalidDataException("There are errors in the format of the P2 PGM");

        String[] content = preParsedLine.split(" ");

        int width = Integer.parseInt(content[1]);
        int height = Integer.parseInt(content[2]);
        int maxVal = Integer.parseInt(content[3]);

        if(maxVal<0 || maxVal>255) throw new InvalidDataException("Max Value should be between 0 and 255");

        if(content.length != width * height + 4) throw new InvalidDataException("Incorrect number of pixel values");

        int[][] pixelValues;

        //TODO Fill pixel Values


        return new PGM();

    }


}
