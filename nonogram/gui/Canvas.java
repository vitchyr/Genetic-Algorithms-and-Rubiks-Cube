package nonogram.gui;

import java.awt.Color;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Graphics;

import java.util.*;

import nonogram.*;

public class Canvas extends JPanel {

    Nonogram nonogram;
    Solution solution;
    int SQ_WIDTH = 20;
    int LABEL_WIDTH = 60;
    int LABEL_HEIGHT = 70;

    public Canvas() {
    }

    public void setNonogram(Nonogram nonogram) {
        this.nonogram = nonogram;

        int gridWidth = SQ_WIDTH * nonogram.getColumnHeaders().size();
        int gridHeight = SQ_WIDTH * nonogram.getRowHeaders().size();

        int panelWidth = gridWidth + LABEL_WIDTH;
        int panelHeight = gridHeight + LABEL_HEIGHT;

        setPreferredSize(new Dimension(panelWidth, panelHeight));
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getBounds().width, getBounds().height);
        
        int xMax = LABEL_WIDTH + nonogram.getColumnHeaders().size() * SQ_WIDTH;
        int yMax = LABEL_HEIGHT + nonogram.getRowHeaders().size() * SQ_WIDTH;

        int x = LABEL_WIDTH;
        int y = LABEL_HEIGHT;

        if (solution != null) {
            g.setColor(Color.BLACK);
            for (int i = 0; i < nonogram.getColumnHeaders().size(); i++) {

                for (int j = 0; j < nonogram.getRowHeaders().size(); j++) {

                    if (solution.getArray()[j][i]) {
                        g.fillRect(x + 2, y + 2, SQ_WIDTH - 3, SQ_WIDTH - 3);
                    }
                    y += SQ_WIDTH;
                }

                x += SQ_WIDTH;
                y = LABEL_HEIGHT;
            }
        }

        x = LABEL_WIDTH;
        y = LABEL_HEIGHT;

        g.setColor(Color.GRAY);
        for (ArrayList<Integer> header : nonogram.getColumnHeaders()) {
            g.setColor(Color.GRAY);
            g.drawLine(x, 0, x, yMax);

            int step = 15;
            int yLabel = 15;

            for (Integer number : header) {
                g.drawString(number.toString(), x + 6, yLabel);
                yLabel += step;
            }

            x += SQ_WIDTH;
        }
        g.drawLine(x, 0, x, yMax);

        for (ArrayList<Integer> header : nonogram.getRowHeaders()) {
            g.drawLine(0, y, xMax, y);
            y += SQ_WIDTH;

            String label = "";

            for (Integer number : header) {
                label += number.toString() + " ";
            }

            g.drawString(label, 5, y - 5);
        }
        g.drawLine(0, y, xMax, y);
        
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
        solution.evaluate();
        this.repaint();
    }
    
    public Solution getSolution(){
        return solution;
    }

    public Nonogram getNonogram() {
        return nonogram;
    }
}