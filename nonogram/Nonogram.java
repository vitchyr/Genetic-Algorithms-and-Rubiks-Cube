package nonogram;

import java.util.*;

public class Nonogram {

    ArrayList<ArrayList<Integer>> rowHeaders, columnHeaders;

    public Nonogram(ArrayList rowHeaders, ArrayList columnHeaders) {
        this.rowHeaders = rowHeaders;
        this.columnHeaders = columnHeaders;
    }

    public static Nonogram fromFile(String text) {
        ArrayList<ArrayList> rowHeaders = new ArrayList();
        ArrayList<ArrayList> columnHeaders = new ArrayList();

        String[] headerSets = text.split("=");

        List currentHeaders = rowHeaders;
        for (String headerSet : headerSets) {

            //split tries to interpret the pipe as a regex!
            String[] headers = headerSet.split("\\|");
         
            for (String headerString : headers) {
                List header = new ArrayList();
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
    
    public ArrayList<ArrayList<Integer>> getColumnHeaders() {
        return columnHeaders;
    }

    public ArrayList<ArrayList<Integer>> getRowHeaders() {
        return rowHeaders;
    }
}
