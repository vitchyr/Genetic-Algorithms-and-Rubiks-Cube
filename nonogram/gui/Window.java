package nonogram.gui;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nonogram.*;

public class Window extends JFrame implements ActionListener {

    Main main;
    final Canvas canvas;
    final JLabel label;
    JPanel panel, buttonPanel;
    JButton startButton, loadButton;
    JTextField textfield;

    public Window(Main main) {
        super("Nonogram Genetic Algorithm");
        this.main = main;
        
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
        startButton.addActionListener(this);
        startButton.setActionCommand("start");
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

    @Override
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()) {
            case "start":
                main.startWorker(canvas.getSolution());
                startButton.setText("Stop");
                startButton.setActionCommand("stop");
                break;
            case "stop":
                main.cancelWorker();
                startButton.setText("Start");
                startButton.setActionCommand("start");
                break;
            case "load":
                break;
        }
    }
}
