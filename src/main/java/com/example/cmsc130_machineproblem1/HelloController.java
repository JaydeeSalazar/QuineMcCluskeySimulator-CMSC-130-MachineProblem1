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

    public String[] convertToBinary(String mintermsReceived) {
        String[] test = mintermsReceived.split(",");
        onesGroups = new ArrayList<>(); // Initialize onesGroups ArrayList

        // Determine the highest value minterm first
        int highest = -1;
        for (int i = 0; i < test.length; i++) {
            int currentValue = Integer.parseInt(test[i]);
            if (currentValue > highest) {
                highest = currentValue;
            }
        }

        // Now we determine how many variables to use, based on the string length of the highest
        numOfVariables = Integer.toBinaryString(highest).length();
        System.out.println(numOfVariables + " variables needed");

        // Now we convert them to our variables

        for (int i = 0; i < test.length-1; i++) {
            String converted = Integer.toBinaryString(Integer.parseInt(test[i]));
            Term temp = new Term(Integer.parseInt(converted), numOfVariables);

            int numOfOnes = temp.getOnesNum(); // Get the number of ones in the Term
            // Ensure that the ArrayList for numOfOnes exists
            while (onesGroups.size() <= numOfOnes) {
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
