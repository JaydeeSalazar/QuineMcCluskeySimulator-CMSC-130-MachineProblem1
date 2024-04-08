package com.example.cmsc130_machineproblem1;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Collections;

public class HelloController {
    @FXML
    private TextField mintermsText;

    @FXML
    private TextField variablesText;

    @FXML
    private TextArea resultText;

    public int numOfVariables;

    private ArrayList<ArrayList<Term>> onesGroups;

    private ArrayList<Term> unusedTerms = new ArrayList<>();

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
        ArrayList<Integer> minterms = new ArrayList<>();
        for (int i = 0; i < test.length; i++)
        {
            minterms.add(Integer.parseInt(test[i]));
        }
        Collections.sort(minterms);
        onesGroups = new ArrayList<>(); // Initialize onesGroups ArrayList

        int highest = Collections.max(minterms);

        // Determines how many variables to use, based on the string length of the
        // binary rep of the highest valued minterm
        numOfVariables = Integer.toBinaryString(highest).length();
        System.out.println(numOfVariables + " variables needed");

        // Now we convert them to our variables
        for (int i = 0; i < test.length; i++)
        {
            Term temp = new Term(Integer.parseInt((test[i])), numOfVariables);

            int numOfOnes = temp.getOnesNum(); // Get the number of ones in the Term
            // Ensure that the ArrayList for numOfOnes exists
            while (onesGroups.size() <= numOfOnes) // Only adds if not enough groups
            {
                onesGroups.add(new ArrayList<>());
            }
            // Add the Term to the appropriate ArrayList in onesGroups
            onesGroups.get(numOfOnes).add(temp);
           // System.out.println(converted);


        }

        // Okay now, we need to combine the terms

        for (int i = 0; i < onesGroups.size()-1; i++){
            for (int j = 0; j < onesGroups.get(i).size(); j++){
                for (int k = 0; k < onesGroups.get(i+1).size(); k++){
                    int indexOfDifference = checkDifference(onesGroups.get(i).get(j), onesGroups.get(i+1).get(k));
                    if (indexOfDifference>=0){
                        onesGroups.get(i).get(j).setUsed(true);
                        onesGroups.get(i+1).get(k).setUsed(true);
                    }
                    System.out.println("Comparing: " + Integer.parseInt(onesGroups.get(i).get(j).getBinaryRep(), 2) + " | " + Integer.parseInt(onesGroups.get(i+1).get(k).getBinaryRep(), 2) + ": " + indexOfDifference);
                    System.out.println((k+1) + " of " + (onesGroups.get(i+1).size()));
                    if (k == onesGroups.get(i+1).size()-1){
                        if (!onesGroups.get(i).get(j).isUsed()){
                            unusedTerms.add(onesGroups.get(i).get(j));
                            System.out.println(onesGroups.get(i).get(j).getBinaryRep() + " was never used");
                        }
                    }
                }
            }
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

    //public void groupTerms

}
