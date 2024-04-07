module com.example.cmsc130_machineproblem1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cmsc130_machineproblem1 to javafx.fxml;
    exports com.example.cmsc130_machineproblem1;
}