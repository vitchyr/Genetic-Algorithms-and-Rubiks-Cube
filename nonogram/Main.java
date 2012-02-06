package nonogram;

import nonogram.gui.Window;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public Main() {
        Nonogram nonogram = nonogramFromFile("puzzles/test1.dat");

        Window window = new Window();
        window.setNonogram(nonogram);
    }

    public Nonogram nonogramFromFile(String path) {
        Nonogram nonogram = null;
        
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                    this.getClass().getResourceAsStream(path)));

            String line;
            while ((line = br.readLine()) != null) {
                nonogram = Nonogram.fromFile(line);
            }

        } catch (IOException e) {
            System.err.println("File not found");
        }  
        
        return nonogram;
    }

    public static void main(String[] args) {
        Main main = new Main();
    }
}
