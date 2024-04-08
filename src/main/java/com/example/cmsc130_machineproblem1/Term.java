package com.example.cmsc130_machineproblem1;

import java.util.ArrayList;

public class Term
{
    // Stores the minterm integers involved in forming the term
    private ArrayList<Integer> groups;

    // Stores the binary representation of the term
    private String binaryRep;

    // Stores the numbers of '1' in the term
    private int onesNum;

    // Constructor for the initialization of a brandnew term
    // that consists of only one minterm

    public Term(int minterm, int numOfVariables)
    {
        String binaryRep = Integer.toBinaryString(minterm);

        while (binaryRep.length() < numOfVariables) { //Add leading zeros in accordance to number of variables
            binaryRep = "0" + binaryRep;
        }

        groups = new ArrayList<Integer>(); // Initialize the groups array/must contain the integer minterm
        groups.add(minterm);

        onesNum = 0; // Count number of ones
        for (int i = 0; i < binaryRep.length(); i++){
            if(binaryRep.charAt(i) == '1')
                onesNum++;
        }
    }

    public String getBinaryRep() {
        return binaryRep;
    }

    public ArrayList<Integer> getGroups() {
        return groups;
    }

    public int getOnesNum() {
        return onesNum;
    }

}
