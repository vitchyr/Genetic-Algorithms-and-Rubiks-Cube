package nonogram;

import nonogram.gui.Window;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class Main {

    final Window window;
    Random random;
    AlgorithmWorker worker;
    boolean cancelTask;
    int gen;

    public Main() {
        Nonogram nonogram = nonogramFromFile("puzzles/" + "test1" + ".dat");
        random = new Random();

        window = new Window(this);
        window.setNonogram(nonogram);
    }

    private class AlgorithmWorker extends SwingWorker<Void, boolean[][]> {

        ArrayList<Solution> population;
        static final int POP_SIZE = 50;
        static final int ELITE_SIZE = 2;

        public AlgorithmWorker(Solution seedSolution) {
            super();

            population = new ArrayList();
            
            if (seedSolution != null) {
                population.add(seedSolution);
            }

            while (population.size() < POP_SIZE) {
                Solution solution = new Solution(window.getNonogram());
                solution.generateRandomSol();

                population.add(solution);
            }
        }

        @Override
        public Void doInBackground() {
            while (!cancelTask) {
                doGeneration();
                gen++;

                publish(population.get(0).getArray());

            }

            return null;
        }

        @Override
        protected void process(List<boolean[][]> arrays) {
            Solution solution = new Solution(window.getNonogram());
            solution.setArray(arrays.get(arrays.size() - 1));
            window.setSolution(solution);

            solution.evaluate();
            window.setLabelText(gen, solution.getFitness());
        }

        public void doGeneration() {
            for (Solution solution : population) {
                solution.evaluate();
            }

            Comparator comparator = Collections.reverseOrder();
            Collections.sort(population, comparator);

            ArrayList<Solution> newPopulation = new ArrayList();

            //Elitism
            for (int i = 0; i < ELITE_SIZE; i++) {
                newPopulation.add(population.get(i));
            }

            int[] weights = new int[population.size()];
            for (int i = 0; i < population.size(); i++) {
                weights[i] = population.get(i).getFitness();
            }

            crossover:
            while (!haveSolution()) {
                Solution parent1 = population.get(getWeightedRandom(weights));
                Solution parent2 = population.get(getWeightedRandom(weights));

                Solution[] offspring = parent1.crossover(parent2, random.nextFloat());

                for (int i = 0; i < 2; i++) {
                    offspring[i].mutate();
                    newPopulation.add(offspring[i]);

                    if (newPopulation.size() == POP_SIZE - ELITE_SIZE) {
                        break crossover;
                    }
                }
            }

            population = newPopulation;
        }

		private boolean haveSolution() {
			return population.get(0).isComplete();
		}
    };

    public int getWeightedRandom(int[] weights) {
        float[] runningTotals = new float[weights.length];

        runningTotals[0] = weights[0];
        for (int i = 1; i < weights.length; i++) {
            runningTotals[i] = runningTotals[i - 1] + (float) weights[i];
        }

        float randomNumber = random.nextFloat();

        for (int i = 0; i < weights.length; i++) {
            if (randomNumber < runningTotals[i]) {
                return i;
            }
        }

        return -1;
    }

    public static Nonogram nonogramFromFile(String location) {
        Nonogram nonogram = null;

        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                    Main.class.getResourceAsStream(location)));

            String line;
            while ((line = br.readLine()) != null) {
                nonogram = Nonogram.fromFile(line);
            }

        } catch (IOException e) {
            System.err.println("File not found");
        }

        return nonogram;
    }

    public void startWorker(Solution solution) {
        cancelTask = false;
        worker = new AlgorithmWorker(solution);
        worker.execute();
    }

    public void cancelWorker() {
        cancelTask = true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new Main();
            }
        });
    }
}
