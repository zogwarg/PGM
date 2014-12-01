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

        Pattern comment = Pattern.compile("#.*$");
        Pattern number = Pattern.compile("[0-9]+$");

        int size lines.size();

        if (size<5) return false;
        if(lines.get(0)!='P2') return false;
        if(!comment.matcher(lines.get(1)).matches()) return false;
        if(number.matcher(lines.get(2)).matches() && number.matcher(lines.get(3)).matches() && number.matcher(lines.get(4)).matches()) {
            int width = (int) lines.get(2);
            int height = (int) lines.get(3);
            int maxVal = (int) lines.get(4);

            if(size != height + 3 || maxVal <0 || maxVal > 255 ) return false;

            String numberToken = "[0-9]{1,3}";
            String lineFormat = "";

            for (int i=0;i<width-1;i++ ) {
                lineFormat+=numberToken+" "
            }

            lineFormat+=numberToken+"$"


        } else {
            return false;
        }
    }


}
