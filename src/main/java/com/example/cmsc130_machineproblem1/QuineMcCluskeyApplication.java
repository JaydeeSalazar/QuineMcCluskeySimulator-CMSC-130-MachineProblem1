package com.example.cmsc130_machineproblem1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class QuineMcCluskeyApplication extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(QuineMcCluskeyApplication.class.getResource("QuineMcCluskeySimulatorGUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Quine-McCluskey Method Calculator");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}
