package com.example.kalkulackaobedu;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Objects;

public class HelloController {



    public TextField nameField;
    public TextField costField;
    public Button addButton;
    public HashMap<String,Integer> database;
    public TextArea DatabaseOutput;


    public ToolBar Record;
    public Label RecordContent;
    public TextField AddMoneyField;


    public VBox ListOfRecords;
    public MenuItem removeDatabaseButton;
    public MenuItem closeButton;
    public MenuItem servicePayment;

    public HelloController() {
        nameField = new TextField();
        costField = new TextField();
        addButton = new Button();
        DatabaseOutput = new TextArea();
        database = new HashMap<>();
    }

    public void AddRecord() {
        if (!Objects.equals(nameField.getText(), "")){
            if (Objects.equals(costField.getText(), "")){
                database.put(nameField.getText(),0);
            } else {
                if (database.containsKey(nameField.getText())){
                    database.replace(nameField.getText(),Integer.parseInt(costField.getText()) + database.get(nameField.getText()));
                } else {
                    database.put(nameField.getText(), Integer.parseInt(costField.getText()));
                }
            }
        }
        RefreshDB();
    }

    public void MakeRecord(String name, String money){
        Record = new ToolBar();
        RecordContent = new Label();
        RecordContent.setText(name + " dluží " + money + " Kč");
        AddMoneyField = new TextField();
        Button deleteRecord = new Button();
        deleteRecord.setText("x");
        Record.getItems().addAll(RecordContent, AddMoneyField, deleteRecord);
        ListOfRecords.getChildren().add(Record);
    }

    public void DeleteRecord(){
        //database.remove(this.Record.getItems());
        RefreshDB();
    }

    public void RefreshDB(){
        ListOfRecords.getChildren().clear();
        for (int i = 0; i < database.size(); i++) {
            MakeRecord(database.keySet().toArray()[i].toString(), database.values().toArray()[i].toString());
        }
    }

    public void DeleteAllRecords(ActionEvent actionEvent) {
        database = new HashMap<>();
        RefreshDB();
    }

    public void CloseApp(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void AddServiceCost(ActionEvent actionEvent) {
        Stage stage = new Stage();
        stage.show();
        stage.setResizable(false);
    }
}