package nonogram.gui;

import java.awt.Color;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Graphics;

import nonogram.Nonogram;

public class Canvas extends JPanel {
    
    Nonogram nonogram;
    
    int SQ_WIDTH = 20;
    int LABEL_WIDTH = 60;
    int LABEL_HEIGHT = 70;
    
    public Canvas(){
            
    }
    
    public void setNonogram(Nonogram nonogram){
        this.nonogram = nonogram;
        
        int gridWidth = SQ_WIDTH * nonogram.getColumnHeaders().size();
        int gridHeight = SQ_WIDTH * nonogram.getRowHeaders().size();
        
        int panelWidth = gridWidth + LABEL_WIDTH;
        int panelHeight = gridHeight + LABEL_HEIGHT;
        
        setPreferredSize(new Dimension(panelWidth, panelHeight));
    }
    
    @Override
    public void paintComponent(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getBounds().width, getBounds().height);
    }

}
