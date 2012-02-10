package nonogram;

import java.util.Random;

public class Solution implements Comparable<Solution> {

    private boolean[][] potentialSol;
    private int rNum, cNum;
    private Nonogram nonogram;

    public Solution(Nonogram nonogram) {
        rNum = nonogram.getRowHeaders().size();
        cNum = nonogram.getColumnHeaders().size();
        this.nonogram = nonogram;

        potentialSol = new boolean[rNum][cNum];
    }

    public void generateSol() {
        Random randGen = new Random();

        for (boolean[] array : potentialSol) {
            for (boolean block : array) {
                block = randGen.nextBoolean();
            }
        }
    }

    //work to do
    public Solution[] crossover(Solution solution, float random) {       
        Solution[] offspring = new Solution[2];

        for (int i = 0; i < this.potentialSol.length; i++) {
            for (int j = 0; j < this.potentialSol[i].length; j++) {
                offspring[0].getPotentialSol()[i][j] = this.potentialSol[i][j];
                offspring[1].getPotentialSol()[i][j] = 
                    solution.getPotentialSol()[i][j];
            }
        }
        
        return offspring;
    }

    public int compareTo(Solution solution) {
        return this.fitness - anotherInstance.fitness;
    }
    //public int evaluate(Nonogram ng){
    //for(row :
    //}

    public Nonogram getNonogram() {
        return nonogram;
    }

    public boolean[][] getPotentialSol() {
        return potentialSol;
    }
}
