package com.example.kalkulackaobedu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Objects;

public class HelloController {
    public TextField nameField;
    public TextField costField;
    public Button addButton;
    public HashMap<String, Integer> database = new HashMap<>();
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
    }

    public void AddRecord() {
        if (!Objects.equals(nameField.getText(), "")) {
            if (Objects.equals(costField.getText(), "")) {
                database.put(nameField.getText(), 0);
            } else {
                if (database.containsKey(nameField.getText())) {
                    database.replace(nameField.getText(), Integer.parseInt(costField.getText())
                            + database.get(nameField.getText()));
                } else {
                    ArithmeticOperation arithmeticOperation = new ArithmeticOperation(costField.getText());
                    //database.put(nameField.getText(), Integer.parseInt(costField.getText()));
                    database.put(nameField.getText(), arithmeticOperation.getNum());
                }
            }
        }
        RefreshDB();
    }
    public void MakeRecord(String name, String money) {
        Record = new ToolBar();
        RecordContent = new Label();
        RecordContent.setText(name + " dluží " + money + " Kč");
        AddMoneyField = new TextField();
        Button deleteRecord = new Button();
        deleteRecord.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DeleteRecord();
            }
        });
        deleteRecord.setText("x");
        Record.getItems().addAll(RecordContent, AddMoneyField, deleteRecord);
        ListOfRecords.getChildren().add(Record);
    }

    public void DeleteRecord() {
        RefreshDB();
    }

    public void RefreshDB() {
        ListOfRecords.getChildren().clear();
        for (int i = 0; i < database.size(); i++) {
            MakeRecord(database.keySet().toArray()[i].toString(), database.values().toArray()[i].toString());
        }
    }


    public void WriteDBtoFIle() throws IOException {
        File file = new File(".",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + ".txt");
        FileWriter fileWriter = new FileWriter(file);
        for (int i = 0; i < database.size(); i++) {
            fileWriter.write(database.keySet().toArray()[i].toString() + "|"
                    + database.values().toArray()[i].toString() + "\n");
        }
        fileWriter.close();
    }

    public void DeleteAllRecords(ActionEvent actionEvent) {
        database = new HashMap<>();
        RefreshDB();
    }

    public void CloseApp(ActionEvent actionEvent) throws IOException {
        WriteDBtoFIle();
        System.exit(0);
    }

    public void AddServiceCost(ActionEvent actionEvent) throws IOException {
        ServiceFee serviceFee = new ServiceFee();
        serviceFee.setDatabaseRef(database);
        serviceFee.ShowScene();
        RefreshDB();
    }
}