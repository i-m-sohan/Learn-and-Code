package com.BankApp.helper;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputHelper {
    private final Scanner inputs;

    public InputHelper() {
        inputs = new Scanner(System.in);
    }

    public Integer inputInt(){
        int value = inputInt("");
        return value;
    }
    public Integer inputInt(String msg) {
        System.out.println(msg);
        try {
            int value = inputs.nextInt();
            inputs.nextLine();  // Consume newline
            return value;
        } catch (InputMismatchException e) {
            inputs.nextLine();
            return null;
        }
    }

    public String inputString(){
        String stringValue = inputString("");
        return stringValue;
    }

    public String inputString(String msg) {
        System.out.println(msg);
        try {
            return inputs.next();
        } catch (InputMismatchException e) {
            inputs.nextLine();
            return null;
        }
    }

    public Double inputDouble(){
        Double doubleValue = inputDouble("");
        return doubleValue;
    }
    public Double inputDouble(String msg) {
        System.out.println(msg);
        try {
            double value = inputs.nextDouble();
            inputs.nextLine();
            return value;
        } catch (InputMismatchException e) {
            inputs.nextLine();
            return null;
        }
    }

    public Long inputLong(){
        Long longValue = inputLong("");
        return longValue;
    }
    public Long inputLong(String msg) {
        System.out.println(msg);
        try {
            long value = inputs.nextLong();
            inputs.nextLine();  // Consume newline
            return value;
        } catch (InputMismatchException e) {
            inputs.nextLine();
            return null;
        }
    }

    public String inputLine(){
        String stringValue = inputLine("");
        return stringValue;
    }
    public String inputLine(String msg) {
        System.out.println(msg);
        return inputs.nextLine();
    }

    public void closeScanner() {
        inputs.close();
    }
}
