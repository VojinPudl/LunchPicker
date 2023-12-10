package com.example.kalkulackaobedu;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.HashMap;

public class ServiceFee {
    public TextField serviceFeeField;
    public Button addFeeToAllButton;

    public HashMap<String,Integer> databaseRef;

    public ServiceFee(HashMap<String, Integer> databaseRef) {
        this.databaseRef = databaseRef;
        serviceFeeField = new TextField();
        addFeeToAllButton = new Button();
    }

    public void addFeeToAll(ActionEvent actionEvent) {
        System.out.println();
    }
}
