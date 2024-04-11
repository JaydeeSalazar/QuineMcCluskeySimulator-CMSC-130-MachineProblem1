package com.example.cmsc130_machineproblem1;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.regex.Pattern;

public class HelloController {
    @FXML
    private TextField mintermsText;

    @FXML
    private TextField variablesText;

    @FXML
    private TextArea resultText;

    public int numOfVariables;

    private ArrayList<ArrayList<Term>> onesGroups;

    private ArrayList<Integer> minterms;

    private ArrayList<Term> unusedTerms = new ArrayList<>();

    private ArrayList<Term> unsimplifiedTerms = new ArrayList<>();

    private ArrayList<Term> simplifiedTerms = new ArrayList<>();

    private ArrayList<Term> finalTerms = new ArrayList<>();

    @FXML
    protected void clearAction() {
        mintermsText.setText("");
        variablesText.setText("");
        resultText.setText("");
    }

    @FXML
    protected void solveAction()
    {
        simplifiedTerms.clear();
        unusedTerms.clear();
        finalTerms.clear();
        resultText.setText("");



        // Gets the minterms from the string
        String[] test = mintermsText.getText().replaceAll("\\s+","").split(",");
        if (test.length == 1 && Objects.equals(test[0], "")){
            resultText.setText("Blank input");
            return;
        }
        for (String s : test) {
            if (Pattern.matches("[a-zA-Z]+", s)) {
                resultText.setText("Invalid input");
                return;
            }
        }

        // Transfer the minterms into an array that holds integer
        minterms = new ArrayList<>();
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
        while (true){
            int amountSimplified = simplify();
            if (amountSimplified == 0){
                break;
            }
        }


        // Now that we've used the terms we need to simplify them more
        System.out.println("Simplified term size: " + simplifiedTerms.size());
        HashSet<Integer> uniqueIntegers = new HashSet<>();

        ArrayList<Term> uniqueTerms = removeDuplicateTerms(simplifiedTerms);
        for (int i = 0; i < uniqueTerms.size(); i++){
            System.out.println("Simplified term w/o dupe: " + uniqueTerms.get(i).getBinaryRep());
        }


        // Iterate through each term in simplifiedTerms
        System.out.println("Unique term size: " + uniqueTerms.size());
        for (Term term : uniqueTerms) {
            // Clear the unique integers set for each term
            uniqueIntegers.clear();

            // Iterate through the groups of the current term
            for (Integer group : term.getGroups()) {
                // If the current group is not present in any other term, add it to the uniqueIntegers set
                boolean isUnique = true;
                for (Term otherTerm : uniqueTerms) {
                    if (term != otherTerm && otherTerm.getGroups().contains(group)) {
                        isUnique = false;
                        break;
                    }
                }
                if (isUnique) {
                    uniqueIntegers.add(group);
                }
            }

            // If any unique integers were found, add the term to finalTerms
            if (!uniqueIntegers.isEmpty()) {
                finalTerms.add(term);
            }
        }
        System.out.println("Final term size: " + finalTerms.size());
        for (int i = 0; i < finalTerms.size(); i++){
            for (int j : finalTerms.get(i).getGroups()){
                System.out.println("Minterm: " + j);
            }
            minterms.removeAll(finalTerms.get(i).getGroups());
        }

        if (!minterms.isEmpty()){


            ArrayList<Term> candidateTerms = new ArrayList<>();
            for (int i = 0; i < uniqueTerms.size(); i++){
                ArrayList<Integer> tempMinterms = new ArrayList<Integer>();
                tempMinterms.addAll(minterms);
                tempMinterms.removeAll(unusedTerms.get(i).getGroups());
                if (tempMinterms.isEmpty()){
                    candidateTerms.add(uniqueTerms.get(i));
                }
            }

            Term bestCandidateTerm = null;
            for (int i = 0;  i < candidateTerms.size(); i++){
                if (minterms.size() == candidateTerms.get(i).getGroupSize()){
                    bestCandidateTerm = candidateTerms.get(i);
                    break;
                }
                if (bestCandidateTerm == null){
                    bestCandidateTerm = candidateTerms.get(i);
                }
                if (bestCandidateTerm.getGroupSize() > candidateTerms.get(i).getGroupSize()) {
                    bestCandidateTerm = candidateTerms.get(i);
                }
            }

            finalTerms.add(bestCandidateTerm);
            for (int k = 0; k < minterms.size(); k++){
                System.out.println("Remaining Minterm: " + minterms.get(k));
            }
            for (int k = 0; k < bestCandidateTerm.getGroupSize(); k++){
                System.out.println("BCT Minterm: " + bestCandidateTerm.getGroups().get(k));
            }
        }

        System.out.println("Size of final terms: " + (finalTerms.size()));
        if (variablesText.getLength() == 0) {
            generateBooleanExpression();
        }
        else{
            generateCustomBooleanExpression();
        }

    }

    public int simplify(){
        int amountSimplified = 0;
        for (int i = 0; i < onesGroups.size()-1; i++){
            for (int j = 0; j < onesGroups.get(i).size(); j++){
                for (int k = 0; k < onesGroups.get(i+1).size(); k++){
                    int indexOfDifference = checkDifference(onesGroups.get(i).get(j), onesGroups.get(i+1).get(k));
                    if (indexOfDifference>=0){
                        amountSimplified += 1;
                        onesGroups.get(i).get(j).setUsed(true);
                        onesGroups.get(i+1).get(k).setUsed(true);
                        unsimplifiedTerms.add(markBinaryRep(onesGroups.get(i).get(j), onesGroups.get(i+1).get(k), indexOfDifference));
                        System.out.println("Added to unsimplified terms: " + markBinaryRep(onesGroups.get(i).get(j), onesGroups.get(i+1).get(k), indexOfDifference).getBinaryRep());
                    }
                    /*if (k == onesGroups.get(i+1).size()-1){
                        if (!onesGroups.get(i).get(j).isUsed()){
                            System.out.println(onesGroups.get(i).get(j).getBinaryRep() + " was never used");
                            unusedOrFinal.add(onesGroups.get(i).get(j));
                        }
                    }*/
                }
            }
        }
        if (onesGroups.size() == 1){
            simplifiedTerms.addAll(onesGroups.get(0));
            simplifiedTerms.addAll(unusedTerms);
            onesGroups.clear();
            unsimplifiedTerms.clear();
        }
        else {
            unusedTerms.addAll(getUnusedTerms(onesGroups));
            printIsUsedStatus(onesGroups);
            if (amountSimplified == 0) {
                simplifiedTerms.addAll(unsimplifiedTerms);
                simplifiedTerms.addAll(unusedTerms);
                simplifiedTerms.addAll(onesGroups.get((onesGroups.size()) - 1));

            }

            onesGroups.clear();
            fillOnesGroups();
            unsimplifiedTerms.clear();
        }
        System.out.println("Amount simplified: " + amountSimplified + " | Ones groups size: " + onesGroups.size());
        return amountSimplified;
    }

    public ArrayList<Term> removeDuplicateTerms(ArrayList<Term> rawTerms){
        ArrayList<Term> newTerms = new ArrayList<>();
        HashSet<String> binaryReps = new HashSet<>();

        for (Term term : rawTerms){
            String binaryRep = term.getBinaryRep();
            if (!binaryReps.contains(binaryRep)) {
                newTerms.add(term);
                binaryReps.add(binaryRep);
            }
        }

        return newTerms;
    }

    public void generateCustomBooleanExpression() {
        // Iterate through each simplified term
        for (Term term : finalTerms) {
            StringBuilder expression = new StringBuilder();
            String variables = variablesText.getText().replaceAll("\\s+","").replaceAll(",", "");
            if (numOfVariables > variables.length()){
                resultText.setText("Not enough variables");
                return;
            }
            // Convert minterms to their corresponding variables
            for (int i = 0; i < numOfVariables; i++) {
                char variable = variables.charAt(i);
                if (term.getBinaryRep().charAt(i) == '0') {
                    expression.append(variable).append("'");
                } else if (term.getBinaryRep().charAt(i) == '1') {
                    expression.append(variable);
                }
            }

            // Add the term to the result
            if (!expression.isEmpty()) {
                resultText.appendText(expression.toString());
                resultText.appendText(" + ");
            }
        }

        // Remove the extra " + " at the end
        if (resultText.getText().endsWith(" + ")) {
            resultText.deleteText(resultText.getText().length() - 3, resultText.getText().length());
        }
    }

    public void generateBooleanExpression() {
        // Iterate through each simplified term
        for (Term term : finalTerms) {
            StringBuilder expression = new StringBuilder();

            // Convert minterms to their corresponding variables
            for (int i = 0; i < numOfVariables; i++) {
                char variable = (char) ('A' + i);
                if (term.getBinaryRep().charAt(i) == '0') {
                    expression.append(variable).append("'");
                } else if (term.getBinaryRep().charAt(i) == '1') {
                    expression.append(variable);
                }
            }

            // Add the term to the result
            if (!expression.isEmpty()) {
                resultText.appendText(expression.toString());
                resultText.appendText(" + ");
            }
        }

        // Remove the extra " + " at the end
        if (resultText.getText().endsWith(" + ")) {
            resultText.deleteText(resultText.getText().length() - 3, resultText.getText().length());
        }
    }

    public void printIsUsedStatus(ArrayList<ArrayList<Term>> onesGroups) {
        for (ArrayList<Term> termsList : onesGroups) {
            for (Term term : termsList) {
                System.out.println("Binary Rep: " + term.getBinaryRep() + ", isUsed: " + term.isUsed());
            }
        }
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

    public ArrayList<Term> getUnusedTerms(ArrayList<ArrayList<Term>> onesGroups) {
        ArrayList<Term> unusedTerms = new ArrayList<>();
        for (ArrayList<Term> termsList : onesGroups) {
            for (Term term : termsList) {
                if (!term.isUsed()) {
                    unusedTerms.add(term);
                }
            }
        }
        return unusedTerms;
    }

    public Term markBinaryRep(Term term1, Term term2, int indexToBeModified){
        String binaryRep = term1.getBinaryRep();
        binaryRep = binaryRep.substring(0, indexToBeModified) + "-" + binaryRep.substring(indexToBeModified+1);
        return new Term(binaryRep, term1.getGroups(), term2.getGroups());
    }

    public void fillOnesGroups(){
        for (Term newTerm : unsimplifiedTerms) {

            System.out.println("Filling onesGroup with: " + newTerm.getBinaryRep());
            for (int i : newTerm.getGroups()){
                System.out.println("Minterm: " + i);
            }
            int numOfOnes = newTerm.getOnesNum(); // Get the number of ones in the Term
            // Ensure that the ArrayList for numOfOnes exists
            while (onesGroups.size() <= numOfOnes) // Only adds if not enough groups
            {
                onesGroups.add(new ArrayList<>());
            }
            // Add the Term to the appropriate ArrayList in onesGroups
            onesGroups.get(numOfOnes).add(newTerm);
        }
    }

}
