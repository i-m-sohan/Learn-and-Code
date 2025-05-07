package util;

import java.util.Scanner;

public class InputHelper {
    public static String getUserInput(String prompt) {
        System.out.print(prompt);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();
        scanner.close();
        return input;
    }
}