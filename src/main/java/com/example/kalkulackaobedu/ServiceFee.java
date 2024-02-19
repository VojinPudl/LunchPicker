package com.example.kalkulackaobedu;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.HashMap;

public class ServiceFee {
    public static Stage stage;
    public static HashMap<String, Integer> databaseRef;
    public static HashMap<String, Integer> databaseRefMain;
    public TextField serviceFeeField;
    public Button addFeeToAllButton;
    public int fee;

    public ServiceFee() {
        serviceFeeField = new TextField();
        addFeeToAllButton = new Button();

    }

    public static HashMap<String, Integer> getDatabaseRefMain() {
        return databaseRefMain;
    }

    public static void setDatabaseRefMain(HashMap<String, Integer> databaseRefMain) {
        ServiceFee.databaseRefMain = databaseRefMain;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public HashMap<String, Integer> getDatabaseRef() {
        return databaseRef;
    }

    public void setDatabaseRef(HashMap<String, Integer> databaseRef) {
        ServiceFee.databaseRef = databaseRef;
    }

    public void ShowScene() throws IOException {
        stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Service-fee.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 200, 50);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Kalkulačka");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.showAndWait();
    }

    public void addFeeToAll() {
        if (getDatabaseRef().isEmpty())
            return;
        setFee((int) Math.floor((double) Integer.parseInt(serviceFeeField.getText()) / getDatabaseRef().size()));
        for (int i = 0; i < getDatabaseRef().size(); i++) {
            getDatabaseRef().replace((String) getDatabaseRef().keySet().toArray()[i],
                    getDatabaseRef().get((String) getDatabaseRef().keySet().toArray()[i]) + fee);
            getDatabaseRefMain().replace((String) getDatabaseRef().keySet().toArray()[i],
                    getDatabaseRefMain().get((String) getDatabaseRefMain().keySet().toArray()[i]) + fee);
        }
        stage.close();
    }

    public void AddByKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (serviceFeeField.getText() != null)
                addFeeToAll();
        }
    }
}