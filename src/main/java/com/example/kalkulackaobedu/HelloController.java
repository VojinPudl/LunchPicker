package com.example.kalkulackaobedu;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    public Button AddMoneyButton;


    public VBox ListOfRecords;
    public MenuItem removeDatabaseButton;
    public MenuItem closeButton;
    public MenuItem servicePayment;
    public MenuItem loadDBbutton;
    public MenuItem saveDBbutton;
    public MenuItem calcButton;
    public Tab DatabaseFull;
    public Tab TodayDB;
    public VBox fullDB;

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
                ArithmeticOperation arithmeticOperation = new ArithmeticOperation(costField.getText());
                if (database.containsKey(nameField.getText())) {
                    database.replace(nameField.getText(), arithmeticOperation.getNum()
                            + database.get(nameField.getText()));
                } else database.put(nameField.getText(), arithmeticOperation.getNum());
            }
        }
        RefreshDB();
        nameField.setText("");
        costField.setText("");
    }

    public void MakeRecord(String name, String money) {
        Record = new ToolBar();
        RecordContent = new Label();
        RecordContent.setText(name + " dluží " + money + " Kč");
        AddMoneyButton = new Button();
        AddMoneyButton.setOnAction(event -> {
            nameField.setText(name);
            RefreshDB();
        });

        Button deleteRecord = new Button();
        deleteRecord.setOnAction(event -> {
            database.remove(name, Integer.valueOf(money));
            RefreshDB();
        });
        AddMoneyButton.setText("Přidat");
        deleteRecord.setText("x");
        Record.getItems().addAll(RecordContent, AddMoneyButton, deleteRecord);
        ListOfRecords.getChildren().add(Record);
    }

    public void RefreshDB() {
        ListOfRecords.getChildren().clear();
        for (int i = 0; i < database.size(); i++) {
            MakeRecord(database.keySet().toArray()[i].toString(), database.values().toArray()[i].toString());
        }
    }

    public void WriteAll(){
        for (int i = 0; i < database.size(); i++) {
            MakeRecord(database.keySet().toArray()[i].toString(), database.values().toArray()[i].toString());
        }
    }


    public void WriteDBtoFIle() throws IOException {
        File file = new File(".",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + ".bin");
        FileWriter fileWriter = new FileWriter(file);
        for (int i = 0; i < database.size(); i++) {
            fileWriter.write(database.keySet().toArray()[i].toString() + "|"
                    + database.values().toArray()[i].toString() + "\n");
        }
        fileWriter.close();
    }

    public void DeleteAllRecords() {
        database = new HashMap<>();
        RefreshDB();
    }

    public void CloseApp() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Close-Sure.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 200, 100);
        stage.setTitle("Kalkulačka");
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.showAndWait();

        //System.exit(0);
    }

    public void AddServiceCost() throws IOException {
        ServiceFee serviceFee = new ServiceFee();
        serviceFee.setDatabaseRef(database);
        serviceFee.ShowScene();
        RefreshDB();
    }

    public void EnterAddRecort(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            AddRecord();
        }
    }

    public void loadDB() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.bin"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                ArrayList<String[]> arrayList = new ArrayList<>();
                while ((line = reader.readLine()) != null) {
                    String[] temp = line.split("\\|");
                    arrayList.add(temp);
                    System.out.println(line);
                }
                for (String[] strings : arrayList) {
                    database.put(strings[0], Integer.valueOf(strings[1]));
                    MakeRecord(strings[0], strings[1]);
                }
            }
        }
    }

    public void showAll() throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(".\\\\src\\\\main\\\\resources\\\\com\\\\example\\\\kalkulackaobedu\\\\All.bin"))) {
            String line;
            ArrayList<String[]> arrayList = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] temp = line.split("\\|");
                arrayList.add(temp);
                System.out.println(line);
            }
            for (String[] strings : arrayList) {
                database.put(strings[0], Integer.valueOf(strings[1]));
                MakeRecord(strings[0], strings[1]);
            }
        }
        System.out.println();
    }

    public void saveDB() throws IOException {
        WriteDBtoFIle();
    }

    public void OpenCalc() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("calc.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 120);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Kalkulačka");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.showAndWait();
    }
}