package nonogram;

import java.util.*;

public class Nonogram {

    List rowHeaders, columnHeaders;

    public Nonogram(List rowHeaders, List columnHeaders) {
        this.rowHeaders = rowHeaders;
        this.columnHeaders = columnHeaders;
    }

    public static Nonogram fromFile(String text) {
        ArrayList rowHeaders = new ArrayList();
        ArrayList columnHeaders = new ArrayList();

        String[] headerSets = text.split("=");

        ArrayList currentHeaders = rowHeaders;
        for (String headerSet : headerSets) {

            //split tries to interpret the pipe as a regex!
            String[] headers = headerSet.split("\\|");
         
            for (String headerString : headers) {
                ArrayList header = new ArrayList();
                String[] numberStrings = headerString.split(",");
                
                for(String numberString : numberStrings){
                    header.add(Integer.parseInt(numberString));
                }
                
                currentHeaders.add(header);
            }
            
            currentHeaders = columnHeaders;
        }

        Nonogram nonogram = new Nonogram(rowHeaders, columnHeaders);
        return nonogram;
    }
}
