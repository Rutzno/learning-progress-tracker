package com.diarpy;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Learning Progress Tracker");
        processCommand();
    }

    public static void processCommand() {
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Bye!");
                break;
            } else if (input.isEmpty() || input.isBlank()) {
                System.out.println("No input.");
            } else {
                System.out.println("Error: unknown command!");
            }
        } while (true);
    }
}
