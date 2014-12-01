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

    public Loader(String path) {
        this.path = path;
        BufferedReader reader;
        lines = new ArrayList();

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

    public boolean IsValid() {
        //boolean validity = true;

        Pattern p2 = Pattern.compile("^P2$");
        Pattern comment = Pattern.compile("^#.*$");
        Pattern dimensions = Pattern.compile("^[0-9]+ [0-9]+$}");


        int size = lines.size();

        if (size<4) return false;
        if(!p2.matcher(lines.get(0)).matches()) return false;
        if(!comment.matcher(lines.get(1)).matches()) return false;

        System.out.println("Batch");


        if(dimensions.matcher(lines.get(2)).matches() && ) {
            int dime;
            int width = Integer.parseInt(lines.get(2));
            int height = Integer.parseInt(lines.get(3));
            int maxVal = Integer.parseInt(lines.get(4));

            System.out.println(size+" "+(height+3));

            if(size != height + 3 || maxVal <0 || maxVal > 255 ) return false;

            String numberToken = "[0-9]{1,3}";
            String lineFormat = "";

            for (int i=0;i<width-1;i++ ) {
                lineFormat+=numberToken+" ";
            }

            lineFormat+=numberToken+"$";

            Pattern linePattern = Pattern.compile(lineFormat);

            for (int i=5;i<size;i++) {
                if (!linePattern.matcher(lines.get(i)).matches()) return false;
            }

            return true;

        } else {
            return false;
        }
    }


}
