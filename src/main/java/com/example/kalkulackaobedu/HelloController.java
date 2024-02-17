package com.example.kalkulackaobedu;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    public TextField nameField;
    public TextField costField;
    public Button addButton;

    public HashMap<String, String> commentsDB = new HashMap<>();
    public HashMap<String, Integer> databaseTemp = new HashMap<>();

    public HashMap<String, Integer> databaseFull = new HashMap<>();
    public TextArea DatabaseOutput;


    public ToolBar Record;
    public ToolBar StaticMan;


    public Label RecordContent;
    public Button AddMoneyButton;


    public VBox ListOfRecords;
    public VBox fullDB;


    public MenuItem removeDatabaseButton;
    public MenuItem closeButton;
    public MenuItem servicePayment;
    public MenuItem loadDBbutton;
    public MenuItem saveDBbutton;
    public MenuItem calcButton;


    public Tab DatabaseFull;
    public Tab TodayDB;


    public HelloController() {
        this.nameField = new TextField();
        this.costField = new TextField();
        this.addButton = new Button();
        this.DatabaseOutput = new TextArea();
    }


    public void AddRecord() {
        if (!Objects.equals(nameField.getText(), "")) {
            if (Objects.equals(costField.getText(), "")) {
                databaseTemp.put(nameField.getText(), 0);
            } else {
                ArithmeticOperation arithmeticOperation = new ArithmeticOperation(costField.getText());
                if (databaseTemp.containsKey(nameField.getText())) {
                    databaseTemp.replace(nameField.getText(), arithmeticOperation.getNum() + databaseTemp.get(nameField.getText()));
                    importIntoFullDB(nameField.getText(), arithmeticOperation.getNum() + databaseFull.get(nameField.getText()));
                } else {
                    databaseTemp.put(nameField.getText(), arithmeticOperation.getNum());
                    importIntoFullDB(nameField.getText(), arithmeticOperation.getNum());
                }
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

        TextField popis = new TextField();
        popis.promptTextProperty().set("Popis objednávky.");
        popis.setPrefWidth(300);

        if (commentsDB.containsKey(name)) {
            popis.promptTextProperty().set(commentsDB.get(name));
        }

        popis.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                commentsDB.put(name, popis.getText());
                popis.setText(popis.getText());
            }
        });

        Button deleteRecord = new Button();
        deleteRecord.setOnAction(event -> {
            importIntoFullDB(name, databaseFull.get(name) - databaseTemp.get(name));
            databaseTemp.remove(name, Integer.valueOf(money));
            RefreshDB();
        });
        AddMoneyButton.setText("Přidat");
        deleteRecord.setText("x");
        Record.getItems().addAll(RecordContent, AddMoneyButton, deleteRecord, popis);
        ListOfRecords.getChildren().add(Record);
    }

    public void RefreshDB() {
        ListOfRecords.getChildren().clear();
        PrintDate();
        for (int i = 0; i < databaseTemp.size(); i++) {
            MakeRecord(databaseTemp.keySet().toArray()[i].toString(), databaseTemp.values().toArray()[i].toString());
        }
    }

    public void importIntoFullDB(String name, int cost) {
        databaseFull.replace(name, cost);
        File selectedFile = new File("src/main/resources/com/example/kalkulackaobedu/All.bin");
        try {
            FileWriter fileWriter = new FileWriter(selectedFile);
            for (int i = 0; i < fullDB.getChildren().size(); i++) {
                fileWriter.write(databaseFull.keySet().toArray()[i].toString() + "|" + databaseFull.values().toArray()[i].toString() + "\n");
            }
            fileWriter.close();
            fullDB.getChildren().clear();
            PrintFullDB();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void PrintFullDB() {
        File selectedFile = new File("src/main/resources/com/example/kalkulackaobedu/All.bin");
        try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
            String line;
            ArrayList<String[]> arrayList = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] temp = line.split("\\|");
                arrayList.add(temp);
                System.out.println(line);
            }
            for (String[] strings : arrayList) {
                databaseFull.put(strings[0], Integer.valueOf(strings[1]));
            }
            for (int i = 0; i < databaseFull.size(); i++) {
                StaticMan = new ToolBar();
                Label label = new Label();
                Label inDatabase = new Label("");
                label.setText(databaseFull.keySet().toArray()[i] + " dluží " + databaseFull.values().toArray()[i] + " Kč");
                int ident = i;
                Button addToDB = new Button("Přidat do DB");
                addToDB.setOnAction(event -> {
                    if (!databaseTemp.containsKey(databaseFull.keySet().toArray()[ident].toString())) {
                        databaseTemp.put(databaseFull.keySet().toArray()[ident].toString(), 0);
                        inDatabase.setText("Již dnes objednává.");
                        RefreshDB();
                    }
                });
                Button nullRecord = new Button("Vynulovat");
                nullRecord.setOnAction(event -> {
                    importIntoFullDB(databaseFull.keySet().toArray()[ident].toString(), 0);
                    databaseTemp.remove(databaseFull.keySet().toArray()[ident].toString());
                    RefreshDB();
                });

                StaticMan.getItems().addAll(label, addToDB, nullRecord, inDatabase);
                fullDB.getChildren().add(StaticMan);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void WriteDBtoFIle() throws IOException {
        File file = new File(".", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + ".txt");
        if (file.exists()) {
            file = new File(".", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy_2")) + ".txt");
        }
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write("-----------------------------------------------" + "\n");
        fileWriter.write("Den: ");
        fileWriter.write(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + "\n");
        fileWriter.write("-----------------------------------------------" + "\n");
        int celkem = 0;
        for (int i = 0; i < databaseTemp.size(); i++) {
            fileWriter.write("+ ");
            fileWriter.write(databaseTemp.keySet().toArray()[i].toString() + " si objednal za " + databaseTemp.values().toArray()[i].toString() + " Kč" + "\n");
            celkem += Integer.parseInt(databaseTemp.values().toArray()[i].toString());
            if (commentsDB.containsKey(databaseTemp.keySet().toArray()[i].toString())) {
                fileWriter.write("\t - " + commentsDB.get(databaseTemp.keySet().toArray()[i].toString()) + "\n");
            }
        }
        fileWriter.write("-----------------------------------------------" + "\n");
        fileWriter.write("Celkem za " + celkem + " Kč" + "\n");
        fileWriter.write("-----------------------------------------------" + "\n");
        fileWriter.close();
    }

    public void DeleteAllRecords() {
        databaseTemp = new HashMap<>();
        for (int i = 0; i < databaseFull.size(); i++) {
            importIntoFullDB(databaseFull.keySet().toArray()[i].toString(), 0);
        }
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
        serviceFee.setDatabaseRef(databaseTemp);
        ServiceFee.setDatabaseRefMain(databaseFull);
        serviceFee.ShowScene();
        RefreshDB();
        fullDB.getChildren().clear();
        for (int i = 0; i < databaseTemp.size(); i++) {
            importIntoFullDB(databaseTemp.keySet().toArray()[i].toString(), databaseTemp.get(databaseTemp.keySet().toArray()[i].toString()));
        }
        //////////////////////////////////////////////
        ///     Import servisniho poplatku
        /////////////////////////////////////////////
    }

    public void EnterAddRecort(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            AddRecord();
        }
    }

    public void loadDB() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Preview.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        stage.setTitle("Kalkulačka");
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.showAndWait();

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

    public void PrintDate() {
        ToolBar DateLine = new ToolBar();
        Label textDate = new Label();
        LocalDate date = LocalDate.now();
        textDate.setText(date.getDayOfMonth() + "." + date.getMonth().getValue() + "." + date.getYear());
        DateLine.getItems().add(textDate);
        ListOfRecords.getChildren().add(DateLine);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PrintFullDB();
        PrintDate();
    }
}