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

    public HashMap<String,Integer> databaseRef;

    public ServiceFee() {
        serviceFeeField = new TextField();
        addFeeToAllButton = new Button();

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
        for (int i = 0; i < databaseRef.size(); i++) {
            databaseRef.replace((String) databaseRef.keySet().toArray()[i], (Integer) databaseRef.values().toArray()[i]);
        }
    }
}