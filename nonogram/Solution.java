package nonogram;

import java.util.ArrayList;
import java.util.Random;

public class Solution implements Comparable<Solution> {

	private boolean[][] array;
	private int rNum, cNum;
	private Nonogram nonogram;
	int fitness = -1;
	int maxFitness = -1;

	public static final boolean ON = true;
	public static final boolean OFF = false;
	public static final int WEIGHT_RIGHT_SIZE = 5;
	public static final int WEIGHT_RIGHT_VALUE = 5;
	public static final int WEIGHT_RIGHT_TOTAL = 1;
	public static double MUTATION_RATE = .2;

	public Solution(Nonogram nonogram) {
		rNum = nonogram.getRowHeaders().size();
		cNum = nonogram.getColumnHeaders().size();
		this.nonogram = nonogram;
		
		array = new boolean[rNum][cNum];
		maxFitness = generateMaxFitness();

		
	}

	public void generateRandomSol() {
		Random randGen = new Random();

		for (int r = 0; r < array.length; r++) {
			for (int c = 0; c < array.length; c++) {
				array[r][c] = randGen.nextBoolean();
			}
		}
	}

	public void mutate() {
		Random randGen = new Random();

		for (int r = 0; r < array.length; r++) {
			for (int c = 0; c < array.length; c++) {
				if (randGen.nextDouble() < MUTATION_RATE) {
					array[r][c] = !array[r][c];
				}
			}
		}

	}

	public Solution[] crossover(Solution solution, float random) {
		Solution[] offspring = new Solution[2];

		// initialize offspring
		for (int i = 0; i < offspring.length; i++) {
			offspring[i] = new Solution(nonogram);
		}

		// copy parent chromosomes into offspring chromosomes
		// switch the parent-offspring matching when a random crossover
		// point is reached

		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {

				if (random > (float) ((i * j) / (array.length * array[i].length))) {
					offspring[0].getArray()[i][j] = this.array[i][j];
					offspring[1].getArray()[i][j] = solution.getArray()[i][j];
				} else {
					offspring[1].getArray()[i][j] = this.array[i][j];
					offspring[0].getArray()[i][j] = solution.getArray()[i][j];
				}
			}
		}

		return offspring;
	}

	@Override
	public int compareTo(Solution solution) {
		return this.fitness - solution.getFitness();
	}

	// Calculate and return fitness of solution
	public int evaluate() {
		fitness = 0;

		ArrayList<ArrayList<Integer>> row = nonogram.getRowHeaders();
		ArrayList<ArrayList<Integer>> col = nonogram.getColumnHeaders();

		// If looking at each row, then look at each row score
		for (int a = 0; a < array.length; a++) {
			fitness += getScore(row.get(a), array[a]);
		}

		for (int b = 0; b < array[0].length; b++) {
			fitness += getScore(col.get(b), getCol(array, b));
		}

		return fitness;
	}

	// get score for a particular row or column
	private int getScore(ArrayList<Integer> arrayList, boolean[] bs) {
		int score = 0;
		ArrayList<Integer> bsList = getTally(bs);

		// REWARD FOR HAVING RIGHT NUMBER OF CLUMPS
		if (arrayList.size() == bsList.size()) {
			score += WEIGHT_RIGHT_SIZE;
		}

		// REWARD FOR CORRECT TOTAL
		for (int a = 0; a < arrayList.size(); a++) {
			if (getSum(bs) == getSum(arrayList)) {
				score += WEIGHT_RIGHT_TOTAL;
			}
		}

		// REWARD FOR HAVING RIGHT SIZE CLUMPS
		for (int b = 0; b < arrayList.size(); b++) {
			if (b < bsList.size()) {
				score += (arrayList.get(b) - Math.abs(arrayList.get(b)
						- bsList.get(b)))
						* WEIGHT_RIGHT_VALUE;
			}
		}

		return score;
	}

	private int getSum(boolean[] bs) {
		int sum = 0;
		for (boolean b : bs) {
			if (b) {
				sum++;
			}
		}

		return sum;
	}

	private int getSum(ArrayList<Integer> arrayList) {
		int sum = 0;
		for (Integer i : arrayList) {
			sum += i;
		}

		return sum;
	}

	// Get column b of a boolean[][]
	private boolean[] getCol(boolean[][] pS, int b) {
		boolean[] c = new boolean[array[0].length];
		for (int a = 0; a < array[0].length; a++) {
			c[a] = array[a][b];
		}

		return c;
	}

	// Returns list of chunk sizes
	private ArrayList<Integer> getTally(boolean[] bs) {
		ArrayList<Integer> psList = new ArrayList();

		int streak = bs[0] == ON ? 1 : 0;
		for (int a = 1; a < bs.length; a++) {
			if (bs[a] == OFF) {
				if (streak > 0) {
					psList.add(streak);
					streak = 0;
				}
			} else {
				streak++;
			}
		}

		// If end of row/col is reached with active streak
		if (streak != 0) {
			psList.add(streak);
		}

		return psList;
	}

	public Nonogram getNonogram() {
		return nonogram;
	}

	public boolean[][] getArray() {
		return array;
	}

	public void setArray(boolean[][] array) {
		this.array = array;
	}

	public int getFitness() {
		return fitness;
	}

	// public boolean isComplete() {
	// return ();
	// }
	//
	// private int getTotalMaxScore() {
	// ArrayList<ArrayList<Integer>> col = nonogram.getColumnHeaders();
	// ArrayList<ArrayList<Integer>> row = nonogram.getRowHeaders();
	// int maxScore = 0;
	//
	// for(ArrayList<Integer> c : col) {
	// maxScore += getMaxColumnScore(c, array);
	// }
	//
	//
	//
	// }

	// Calculate and return fitness of solution
	public int generateMaxFitness() {
		int f = 0;

		ArrayList<ArrayList<Integer>> row = nonogram.getRowHeaders();
		ArrayList<ArrayList<Integer>> col = nonogram.getColumnHeaders();

		// If looking at each row, then look at each row score
		for (int a = 0; a < array.length; a++) {
			f += getMaxScore(row.get(a), array[a]);
		}

		for (int b = 0; b < array[0].length; b++) {
			f += getMaxScore(col.get(b), getCol(array, b));
		}

//		fitness = fitness;

		return f;
	}

	private int getMaxScore(ArrayList<Integer> arrayList, boolean[] bs) {
		int score = 0;

		// REWARD FOR HAVING RIGHT NUMBER OF CLUMPS
		// if (arrayList.size() == bsList.size()) {
		score += WEIGHT_RIGHT_SIZE;
		// }

		// REWARD FOR CORRECT TOTAL
		// for (int a = 0; a < arrayList.size(); a++) {
		// if (getSum(bs) == getSum(arrayList)) {
		score += WEIGHT_RIGHT_TOTAL * arrayList.size();
		// }
		// }

		// REWARD FOR HAVING RIGHT SIZE CLUMPS
		for (int b = 0; b < arrayList.size(); b++) {
			// if (b < bsList.size()) {
			score += (arrayList.get(b))
			// - Math.abs(arrayList.get(b)
			// - bsList.get(b)))
					* WEIGHT_RIGHT_VALUE;
			// }
		}

//		score = score;
		return score;
	}

	public boolean isComplete() {
		return getFitness() == getMaxFitness();
	}

	public int getMaxFitness() {
		return maxFitness;
	}

}
