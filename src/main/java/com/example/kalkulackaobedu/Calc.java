package com.example.kalkulackaobedu;

import javafx.scene.control.TextField;

import java.util.Objects;

public class Calc {

    public TextField cenaField;
    public TextField dividerField;
    public TextField finalCost;

    public void StartedDivider() {
     if (Objects.equals(dividerField.getText(), "")){
         finalCost.setText(String.valueOf(0));
     } else {
         finalCost.setText(String.valueOf(Math.floor((double) Integer.parseInt(cenaField.getText())
                 /Integer.parseInt(dividerField.getText()))));
     }
    }

    public void StartedCena() {
        if (Objects.equals(cenaField.getText(), "")){
            finalCost.setText(String.valueOf(0));
        } else {
            finalCost.setText(String.valueOf(Math.floor((double) Integer.parseInt(cenaField.getText())
                    /Integer.parseInt(dividerField.getText()))));
        }
    }
}
