package nonogram.gui;

import javax.swing.JFrame;
import nonogram.Nonogram;

public class Window extends JFrame {
    
    Canvas canvas;
    
    public Window(){
        super("Nonogram Genetic Algorithm");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        canvas = new Canvas();
        add(canvas);
       
        pack();
        setVisible(true);
        setResizable(false);
    }
    
    public void setNonogram(Nonogram nonogram){
        canvas.setNonogram(nonogram);
        pack();
    }
}
