package ec.app.assignment1;
import ec.util.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import ec.*;
import ec.gp.*;
import ec.gp.koza.*;
import ec.simple.*;

public class PartB extends GPProblem implements SimpleProblemForm{
    public static final String P_DATA = "data";
    
    public Object [][] data = new Object[31][569];
    public Object [][] trainingData = new Object[31][284];
    public Object [][] testingData = new Object[31][285];
    public float currentX1;
    public float currentX2;
    public float currentX3;
    public float currentX4;
    public float currentX5;
    public float currentX6;
    public float currentX7;
    public float currentX8;
    public float currentX9;
    public float currentX10;

    public void setup(final EvolutionState state,
                      final Parameter base) {
        // very important, remember this
        super.setup(state,base);

        // verify our input is the right class (or subclasses from it)
        if (!(input instanceof FloatData)) {
            state.output.fatal("GPData class must subclass from " + FloatData.class,
                base.push(P_DATA), null);
        }

        readFromFile();
        shuffle(data);

        // split data into training data and testing data
        for (int j = 0; j < trainingData[0].length; j++) {
            for (int i = 0; i < trainingData.length; i++) {
                trainingData[i][j] = data[i][j];
            }
        }
        for (int j = 0; j < testingData[0].length; j++) {
            for (int i = 0; i < testingData.length; i++) {
                testingData[i][j] = data[i][j+trainingData[0].length];
            }
        }
    }
        public void evaluate(final EvolutionState state, 
        final Individual ind, 
        final int subpopulation,
        final int threadnum) {
    if (!ind.evaluated) {  // don't bother reevaluating
       
       FloatData input = (FloatData)(this.input);
    
       int hits = 0;
       float sum = 0;
       String expected;
       for (int j = 0; j < trainingData[0].length; j++) {
        expected = (String) trainingData[0][j];
        currentX1 = (float) trainingData[1][j];
        currentX2 = (float) trainingData[2][j];
        currentX3 = (float) trainingData[3][j];
        currentX4 = (float) trainingData[4][j];
        currentX5 = (float) trainingData[5][j];
        currentX6 = (float) trainingData[6][j];
        currentX7 = (float) trainingData[7][j];
        currentX8 = (float) trainingData[8][j];
        currentX9 = (float) trainingData[9][j];
        currentX10 = (float) trainingData[10][j];

        
        ((GPIndividual)ind).trees[0].child.eval(
            state,threadnum,input,stack,((GPIndividual)ind),this);
            
            if (input.x >= 0 && expected.equals("M")) {
                hits++;
            }
            else if (input.x < 0 && expected.equals("B")) {
                hits++;
            }
            else {
                sum = sum + 1;
            }                 
       }
    
       // the fitness better be KozaFitness!
       KozaFitness f = ((KozaFitness)ind.fitness);
       f.setStandardizedFitness(state, sum);
       f.hits = hits;
       ind.evaluated = true;
    }
}

public void describe(
        final EvolutionState state, 
        final Individual ind, 
        final int subpopulation, 
        final int threadnum,
        final int log)
        {
            state.output.println("\n\nTesting Results\n=====================", log);

            FloatData input = (FloatData)(this.input);

            String expected;
            for (int j = 0; j < testingData[0].length; j++) {
             expected = (String) testingData[0][j];
             currentX1 = (float) testingData[1][j];
             currentX2 = (float) testingData[2][j];
             currentX3 = (float) testingData[3][j];
             currentX4 = (float) testingData[4][j];
             currentX5 = (float) testingData[5][j];
             currentX6 = (float) testingData[6][j];
             currentX7 = (float) testingData[7][j];
             currentX8 = (float) testingData[8][j];
             currentX9 = (float) testingData[9][j];
             currentX10 = (float) testingData[10][j];
     
             
             ((GPIndividual)ind).trees[0].child.eval(
                 state,threadnum,input,stack,((GPIndividual)ind),this);
                 
                 if (input.x >= 0) {
                     state.output.println("Expected Result: M  " + "Acutal Result: " + expected, log);
                 }
                 else if (input.x < 0) {
                    state.output.println("Expected Result: B  " + "Acutal Result: " + expected, log);
                 }                
            }
        }

private void readFromFile() {
    Scanner scanner;
    File file = new File("wdbc.txt");
    try {
        scanner = new Scanner(file);
        int count1 = 0;

        while (scanner.hasNext()) {
            int count = 0;
            String line = scanner.nextLine();                           //get the line
            String[] parts = line.split(",");                       //split data for every ","
            data[count][count1] = new String(parts[1]);
            count++;
            for (int i = 2; i < parts.length; i++) {
                data[count][count1] = Float.parseFloat(parts[i]);   //cast to float
                count++;
            }
            count1++;

        }
    } catch(FileNotFoundException e) {
        e.printStackTrace();
    }

}

/**
 * Shuffles an array of objects
 * @param array
 */
private void shuffle(Object[][] array) {
    Random random = new Random();
    Object[] temp = new Object[array.length];
    
    for (int j = 0; j < 569; j++) {
        int row1 = random.nextInt(temp.length);
        int row2 = random.nextInt(temp.length);
    
        for (int i = 0; i < temp.length; i++) {
            temp[i] = array[i][row1];
            array[i][row1] = array[i][row2];
            array[i][row2] = temp[i];
        }
    }
   
}
    
}

