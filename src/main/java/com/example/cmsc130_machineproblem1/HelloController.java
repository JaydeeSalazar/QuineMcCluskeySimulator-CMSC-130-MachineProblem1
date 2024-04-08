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

        for (int i = 0; i < test.length; i++) {
            String converted = Integer.toBinaryString(Integer.parseInt(test[i]));
            while (converted.length() < numOfVariables) {
                converted = "0" + converted;
            }

            Term temp = new Term(converted);
            int numOfOnes = temp.numOfOnes; // Get the number of ones in the Term
            // Ensure that the ArrayList for numOfOnes exists
            while (onesGroups.size() <= numOfOnes) {
                onesGroups.add(new ArrayList<>());
            }
            // Add the Term to the appropriate ArrayList in onesGroups
            onesGroups.get(numOfOnes).add(temp);
            System.out.println(converted);
        }

        return test;
    }

    public Term checkDifference(Term term1, Term term2){
        for (int i = 0; i < term1)


        return;
    }

    public Term combineTerms(Term term1, Term term2){



        return;
    }

}
