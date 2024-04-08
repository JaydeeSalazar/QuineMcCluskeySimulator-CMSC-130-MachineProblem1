package com.example.cmsc130_machineproblem1;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class HelloController {
    @FXML
    private TextField mintermsText;

    @FXML
    private TextField variablesText;

    @FXML
    private TextArea resultText;

    public int numOfVariables;

    private ArrayList<ArrayList<Term>> onesGroups; // Corrected ArrayList declaration

    public String defaultVariables = "ABCDEFGHIJKLMNOPQRSTUVWXYZ[/]^_";

    @FXML
    protected void solveAction() {
        String[] minterms = convertToBinary(mintermsText.getText());
        System.out.println("Solved");
    }

    @FXML
    protected void clearAction() {
        mintermsText.setText("");
        variablesText.setText("");
        resultText.setText("");

        System.out.println("Cleared");
    }

    public String[] convertToBinary(String mintermsReceived)
    {
        // Gets the minterms from the string
        String[] test = mintermsReceived.split(",");

        // Transfer the minterms into an array that holds integer
        ArrayList<Integer> test2 = new ArrayList<>();
        for (int i = 0; i < test.length; i++)
        {
            test2.add(parseInt(test[0]));
        }

        onesGroups = new ArrayList<>(); // Initialize onesGroups ArrayList

        // Determines the highest valued minterm integer
        int highest = -1;
        for (int i = 0; i < test.length; i++) {
            int currentValue = Integer.parseInt(test[i]);
            if (currentValue > highest) {
                highest = currentValue;
            }
        }

        // Determines how many variables to use, based on the string length of the
        // binary rep of the highest valued minterm
        numOfVariables = Integer.toBinaryString(highest).length();
        System.out.println(numOfVariables + " variables needed");

        // Now we convert them to our variables
        for (int i = 0; i < test.length-1; i++)
        {
            Term temp = new Term(Integer.parseInt((test[i])), numOfVariables);

            int numOfOnes = temp.getOnesNum(); // Get the number of ones in the Term
            // Ensure that the ArrayList for numOfOnes exists
            while (onesGroups.size() <= numOfOnes)
            {
                onesGroups.add(new ArrayList<>());
            }
            // Add the Term to the appropriate ArrayList in onesGroups
            onesGroups.get(numOfOnes).add(temp);
           // System.out.println(converted);
        }

        return test;
    }

    public int checkDifference(Term term1, Term term2){
        int index = -1;
        for (int i = 0; i < term1.getBinaryRep().length(); i++){
            if (term1.getBinaryRep().charAt(i) != term2.getBinaryRep().charAt(i)){
                if (index >= 0){
                    return -1;
                }
                else{
                    index = i;
                }
            }
        }
        return index; // Finished for loop without encountering more than 1 difference
    }

    public Term combineTerms(Term term1, Term term2, int indexOfDifference){
        Term combinedTerm = new Term(0, numOfVariables);
        String newBinaryRep = term1.getBinaryRep();
        newBinaryRep = newBinaryRep.substring(0,indexOfDifference)+'-'+newBinaryRep.substring(indexOfDifference+1);
        combinedTerm.setBinaryRep(newBinaryRep);
        System.out.println(combinedTerm.getBinaryRep());
        return combinedTerm;
    }

}
