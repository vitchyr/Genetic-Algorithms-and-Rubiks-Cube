package final_project;

import java.io.*;

public class Main {
    
    public Main(){
        try {

        BufferedReader br = new BufferedReader( 
                new InputStreamReader(
                this.getClass().getResourceAsStream("puzzles/test1.dat")));

        String line;
        while ((line = br.readLine()) != null) {
            Nonogram nonogram = Nonogram.fromFile(line);
        }

        } catch (IOException e) {
            System.err.println("File not found");
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
    }
}
