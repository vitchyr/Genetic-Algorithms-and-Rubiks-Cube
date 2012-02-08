import java.util.Random;

public class Solution{
    private Boolean[][] potentialSol;
    private int rNum, cNum;
    public Solution(int rowNum, int colNum){
        rNum = rowNum;
        cNum = colNum;
        potentialSol = new Boolean[rNum][cNum];
        potentialSol = generateSol(potentialSol);
    }
    public Boolean[][] generateSol(Boolean[][] ps){
        Random randGen = new Random();
        Boolean[][] potentialSol = ps;
        for (Boolean[] array : potentialSol){
            for (Boolean block : array){
                block = randGen.nextBoolean();
            }
        }
        return potentialSol;
    }
    public int evaluate(Nonogram ng){
        for(row :
    }
}
