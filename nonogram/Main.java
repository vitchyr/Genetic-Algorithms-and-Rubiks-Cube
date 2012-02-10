package nonogram;

import nonogram.gui.Window;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Main {

    ArrayList<Solution> population;
    Random random;
    Window window;
    static final int POP_SIZE = 50;
    static final int ELITE_SIZE = 2;

    public Main() {
        Nonogram nonogram = nonogramFromFile("puzzles/test1.dat");
        random = new Random();

        window = new Window();
        window.setNonogram(nonogram);

        population = new ArrayList();
        for (int i = 0; i < POP_SIZE; i++) {
            Solution solution = new Solution(nonogram);
            solution.generateRandomSol();

            population.add(solution);
        }

        //temporary
        start();
    }

    public void start() {
        int gen = 0;
        while (true) {
            doGeneration();
            window.setSolution(population.get(0));

            gen++;
            window.setLabelText("", gen);
        }
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
            population.remove(i);
        }

        int[] weights = new int[population.size()];
        for (int i = 0; i < population.size(); i++) {
            weights[i] = population.get(i).getFitness();
        }

        crossover:
        while (true) {
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

    public static Nonogram nonogramFromFile(String path) {
        Nonogram nonogram = null;

        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                    Main.class.getResourceAsStream(path)));

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
