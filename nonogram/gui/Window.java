package nonogram.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import nonogram.Main;
import nonogram.Nonogram;
import nonogram.Solution;

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
		startButton.setEnabled(false);
		buttonPanel.add(startButton, BorderLayout.WEST);

		loadButton = new JButton("Load");
		loadButton.addActionListener(this);
		loadButton.setActionCommand("load");
		buttonPanel.add(loadButton, BorderLayout.CENTER);

		setPreferredSize(new Dimension(600, 600));
		pack();
		setVisible(true);
	}

	public void setNonogram(Nonogram nonogram) {
		canvas.setNonogram(nonogram);
		pack();
	}

	public Nonogram getNonogram() {
		return canvas.getNonogram();
	}

	public void setSolution(Solution solution) {
		assert (solution.getNonogram() == canvas.getNonogram());
		canvas.setSolution(solution);
	}

	public void setLabelText(int gen, int fitness) {
		label.setText("Generation: " + Integer.toString(gen) + "   "
				+ "Highest fitness: " + Integer.toString(fitness)
				+ "    Max Fitness: " + Integer.toString(canvas.getSolution().getMaxFitness()));

		pack();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals("start")) {
			if (canvas.getNonogram() != null) {
				main.startWorker(canvas.getSolution());
				startButton.setText("Stop");
				startButton.setActionCommand("stop");
			}
		} else if (event.getActionCommand().equals("stop")) {
			main.cancelWorker();
			startButton.setText("Start");
			startButton.setActionCommand("start");
		} else if (event.getActionCommand().equals("load")) {
			try {
				JFileChooser chooser = new JFileChooser();
				chooser.setSelectedFile(new File("puzzles/" + "test1" + ".dat"));
				int returnVal = chooser.showOpenDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					// System.out.println("You chose to open this file: " +
					chooser.getSelectedFile().getName();// );
				}

				setNonogram(Main.nonogramFromFile(chooser.getSelectedFile()
						.getAbsolutePath()));

				startButton.setEnabled(true);
			} catch (Exception e) {
				System.out.println(e);
				System.out.println(e.getCause());
				System.err.println("Problem in main method");
			}

		}
	}
}
