package com.example.cmsc130_machineproblem1;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HelloController
{
    @FXML
    private TextField mintermsText;

    @FXML
    private TextField variablesText;

    @FXML
    private TextArea resultText;

    @FXML
    protected void solveAction() {
        

        System.out.println("Solved");
    }

    @FXML
    protected void clearAction() {
        mintermsText.setText("");
        variablesText.setText("");
        resultText.setText("");

        System.out.println("Cleared");
    }


}