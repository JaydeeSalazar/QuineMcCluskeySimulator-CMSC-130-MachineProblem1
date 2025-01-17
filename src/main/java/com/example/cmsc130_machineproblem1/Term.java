package com.example.cmsc130_machineproblem1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Term
{
    // Stores the minterm integers involved in forming the term
    private ArrayList<Integer> groups;

    // Stores the binary representation of the term
    private String binaryRep;

    // Stores the numbers of '1' in the term
    private int onesNum;

    // Determines whether this term was ever used in the process
    private Boolean isUsed = false;

    // Constructor for the initialization of a brandnew term
    // that consists of only one minterm


    public Term(int minterm, int numOfVariables)
    {
        String binaryRep = Integer.toBinaryString(minterm);

        while (binaryRep.length() < numOfVariables) { //Add leading zeros in accordance to number of variables
            binaryRep = "0" + binaryRep;
        }
        this.binaryRep = binaryRep;

        groups = new ArrayList<Integer>(); // Initialize the groups array/must contain the integer minterm
        groups.add(minterm);

        onesNum = 0; // Count number of ones
        for (int i = 0; i < binaryRep.length(); i++){
            if(binaryRep.charAt(i) == '1')
                onesNum++;
        }
    }

    public Term(String newBinaryRep, ArrayList<Integer> group1, ArrayList<Integer> group2)
    {
        this.binaryRep = newBinaryRep;

        onesNum = 0; // Count number of ones
        for (int i = 0; i < binaryRep.length(); i++){
            if(binaryRep.charAt(i) == '1')
                onesNum++;
        }

        groups = new ArrayList<>();
        groups.addAll(group1);
        groups.addAll(group2);

    }

    public String getBinaryRep() {
        return binaryRep;
    }

    public ArrayList<Integer> getGroups() {
        return groups;
    }

    public int getGroupSize() {
        return groups.size();
    }

    public int getOnesNum() {
        return onesNum;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setGroups(ArrayList<Integer> groups) {
        this.groups = groups;
    }

    public void setBinaryRep(String binaryRep) {
        this.binaryRep = binaryRep;
    }

    public void setOnesNum(int onesNum) {
        this.onesNum = onesNum;
    }

    public void setUsed(Boolean used)
    {
        isUsed = used;
    }

}
