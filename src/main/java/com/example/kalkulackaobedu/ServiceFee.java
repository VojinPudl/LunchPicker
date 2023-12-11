package com.example.kalkulackaobedu;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class ServiceFee {
    public TextField serviceFeeField;
    public Button addFeeToAllButton;

    public static HashMap<String,Integer> databaseRef;

    public ServiceFee() {
        serviceFeeField = new TextField();
        addFeeToAllButton = new Button();

    }

    public void setDatabaseRef(HashMap<String, Integer> databaseRef) {
        ServiceFee.databaseRef = databaseRef;
    }

    public HashMap<String, Integer> getDatabaseRef() {
        return databaseRef;
    }

    public void ShowScene() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Service-fee.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 200, 50);
        stage.setTitle("Kalkulačka");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.showAndWait();
    }

    public void addFeeToAll(ActionEvent actionEvent) {
        if (getDatabaseRef().isEmpty())
            return;
        for (int i = 0; i < getDatabaseRef().size(); i++) {
            getDatabaseRef().replace((String) getDatabaseRef().keySet().toArray()[i],
                    (Integer) getDatabaseRef().values().toArray()[i]
                            + Integer.parseInt(serviceFeeField.getText()));
        }
    }
}