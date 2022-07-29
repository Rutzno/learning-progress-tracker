package com.diarpy;

import java.util.Scanner;

/**
 * @author Mack_TB
 * @since 24/06/2022
 * @version 1.0.2
 */

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static int numberOfStudents = 0;
    static String nameRegex = "[A-Za-z\\s]+([A-Za-z]|[-'][A-Za-z])+";
    static String emailRegex = "[a-z.\\d]+@[a-z\\d]+\\.[a-z\\d]+";

    public static void main(String[] args) {
        System.out.println("Learning Progress Tracker");
        processCommand();
    }

    public static void processCommand() {
        String input;
        do {
            input = scanner.nextLine();
            if (input.equals("add students")) {
                addStudent();
            } else if (input.equalsIgnoreCase("exit")) {
                System.out.println("Bye!");
                break;
            } else if (input.isEmpty() || input.isBlank()) {
                System.out.println("No input.");
            } else if (input.equalsIgnoreCase("back")) {
                System.out.println("Enter 'exit' to exit the program.");
            } else {
                System.out.println("Unknown command!");
            }
        } while (true);
    }

    private static void addStudent() {
        System.out.println("Enter student credentials or 'back' to return");
        while (true) {
            String credentials = scanner.nextLine();
            if (credentials.equals("back")) {
                System.out.printf("Total %d students have been added.\n", numberOfStudents);
                break;
            }
            String[] tab = credentials.split("\\s+");
            if (tab.length < 3) {
                System.out.println("Incorrect credentials.");
                continue;
            }
            String firstName = tab[0];
            String email = tab[tab.length - 1];
            String lastName = credentials.substring(credentials.indexOf(" ") + 1, credentials.indexOf(email)-1);

            if (!firstName.matches(nameRegex)) {
                System.out.println("Incorrect first name.");
            } else if (!lastName.matches(nameRegex)) {
                System.out.println("Incorrect last name.");
            } else if (!email.matches(emailRegex)) {
                System.out.println("Incorrect email.");
            } else {
                numberOfStudents++;
                System.out.println("The student has been added.");
            }
        }
    }
}
