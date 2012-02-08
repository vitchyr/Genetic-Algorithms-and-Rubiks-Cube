package nonogram;

import java.util.Random;

public class Solution{
    private Boolean[][] potentialSol;
    private int rNum, cNum;
    private Nonogram nonogram;
    
    public Solution(Nonogram nonogram){        
        rNum = nonogram.getRowHeaders().size();
        cNum = nonogram.getColumnHeaders().size();
        this.nonogram = nonogram;
        
        potentialSol = new Boolean[rNum][cNum];
        generateSol();
    }
    
    public void generateSol(){
        Random randGen = new Random();
        
        for (Boolean[] array : potentialSol){
            for (Boolean block : array){
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
}
