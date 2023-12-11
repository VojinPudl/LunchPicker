package com.example.kalkulackaobedu;

public class ArithmeticOperation {
    int num = 0;

    public ArithmeticOperation(String values) {
        String[] temp = values.replace(" ","").split("\\+");
        for (int i = 0; i < temp.length; i++) {
            num += Integer.parseInt(temp[0]);
        }
    }
    public int getNum() {
        return num;
    }
}
