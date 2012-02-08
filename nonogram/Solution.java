package nonogram;

import java.util.Random;

public class Solution{
    private boolean[][] potentialSol;
    private int rNum, cNum;
    private Nonogram nonogram;
    
    public Solution(Nonogram nonogram){        
        rNum = nonogram.getRowHeaders().size();
        cNum = nonogram.getColumnHeaders().size();
        this.nonogram = nonogram;
        
        potentialSol = new boolean[rNum][cNum];
        generateSol();
    }
    
    public void generateSol(){
        Random randGen = new Random();
        
        for (boolean[] array : potentialSol){
            for (boolean block : array){
                block = randGen.nextBoolean();
            }
        }        
    }
    
    
    //public int evaluate(Nonogram ng){
        //for(row :
    //}

    public Nonogram getNonogram() {
        return nonogram;
    }
    
    public boolean[][] getPotentialSol(){
        return potentialSol;
    }
}
