package com.example.cmsc130_machineproblem1;

import java.util.ArrayList;

public class Term
{
    private String term;

    int numOfOnes;

    private ArrayList<Integer> nums;

    public Term(String binary)
    {
        this.term = binary;

        numOfOnes = 0;
        for (int i = 0; i < term.length(); i++){
            if(term.charAt(i) == '1')
                numOfOnes++;
        }


    }


}
