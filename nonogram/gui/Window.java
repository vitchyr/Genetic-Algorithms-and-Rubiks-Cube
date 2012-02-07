package nonogram.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;

import nonogram.Nonogram;

public class Window extends JFrame {
    
    Canvas canvas;
    JLabel label;
    
    public Window(){
        super("Nonogram Genetic Algorithm");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        canvas = new Canvas();
        getContentPane().add(canvas, BorderLayout.CENTER);
        
        label = new JLabel();
        getContentPane().add(label, BorderLayout.SOUTH);
        
        pack();
        setVisible(true);
        setResizable(false);
    }
    
    public void setNonogram(Nonogram nonogram){
        canvas.setNonogram(nonogram);
        pack();
    }
    
    public void setLabelText(String message, int generation){
        String genString = "Gen: " + Integer.toString(generation);
        
        if(!message.equals("")){
            label.setText(message + " --- " + genString);
        } else {
            label.setText(genString);
        }
        
        
    }
}
