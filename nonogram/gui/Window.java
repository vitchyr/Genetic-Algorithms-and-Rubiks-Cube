package nonogram.gui;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;

import nonogram.*;

public class Window extends JFrame {

    Canvas canvas;
    JLabel label;
    JPanel panel, buttonPanel;
    JButton startButton, loadButton;
    JTextField textfield;

    public Window() {
        super("Nonogram Genetic Algorithm");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        canvas = new Canvas();
        getContentPane().add(canvas, BorderLayout.CENTER);
        
        panel = new JPanel();
        getContentPane().add(panel, BorderLayout.SOUTH);

        label = new JLabel("Press start to begin");
        panel.add(label, BorderLayout.CENTER);
        
        buttonPanel = new JPanel();               
        buttonPanel.setPreferredSize(new Dimension(400, 50));
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        startButton = new JButton("Start");
        buttonPanel.add(startButton, BorderLayout.WEST);
        
        loadButton = new JButton("Load:");
        buttonPanel.add(loadButton, BorderLayout.CENTER);
        
        textfield = new JTextField(10);
        buttonPanel.add(textfield, BorderLayout.CENTER);


        setPreferredSize(new Dimension(400, 400));
        pack();
        setVisible(true);
    }

    public void setNonogram(Nonogram nonogram) {
        canvas.setNonogram(nonogram);
        pack();
    }
    
    public Nonogram getNonogram(){
        return canvas.getNonogram();
    }

    public void setSolution(Solution solution) {
        assert (solution.getNonogram() == canvas.getNonogram());
        canvas.setSolution(solution);
    }

    public void setLabelText(int gen, int fitness) {
        label.setText("Generation: " + Integer.toString(gen) + "   " +
            "Highest fitness: " + Integer.toString(fitness));

        pack();
    }
}
