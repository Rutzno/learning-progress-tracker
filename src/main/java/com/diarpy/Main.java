package com.diarpy;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Mack_TB
 * @since 24/06/2022
 * @version 1.0.3
 */

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static String nameRegex = "[A-Za-z\\s]+([A-Za-z]|[-'][A-Za-z])+";
    static String emailRegex = "[a-z.\\d]+@[a-z\\d]+\\.[a-z\\d]+";
    static Map<String, Student> studentMap = new LinkedHashMap<>();

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
            } else if (input.equals("list")) {
                list();
            } else if (input.equals("add points")) {
                addPoints();
            } else if (input.equals("find")) {
                findStudent();
            }  else if (input.equalsIgnoreCase("exit")) {
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

    private static void findStudent() {
        System.out.println("Enter an id or 'back' to return:");
        while (true) {
            String id = scanner.nextLine();
            if (id.equals("back")) {
                break;
            }
            if (!studentMap.containsKey(id)) {
                System.out.printf("No student is found for id=%s.\n", id);
            } else {
                Student student = studentMap.get(id);
                System.out.printf("%s points: Java=%d; DSA=%d; Databases=%d; Spring=%d\n",
                        id,
                        student.getCourses().get("Java"),
                        student.getCourses().get("DSA"),
                        student.getCourses().get("Databases"),
                        student.getCourses().get("Spring"));
            }
        }
    }

    private static void addPoints() {
        System.out.println("Enter an id and points or 'back' to return:");
        while (true) {
            String idnPoints = scanner.nextLine();
            if (idnPoints.equals("back")) {
                break;
            }
            try {
                String[] tab = idnPoints.split("\\s+");
                String id = tab[0];
                if (!studentMap.containsKey(id)) {
                    System.out.printf("No student is found for id=%s\n", id);
                    continue;
                }
                int[] idnPointTab = convertStringArrayToInt(tab);
                int[] notes = Arrays.copyOfRange(idnPointTab, 1, idnPointTab.length);
                Student student = studentMap.get(id);
                if (student.grade(notes)) {
                    System.out.println("Points updated.");
                }
            } catch (Exception e) {
                System.out.println("Incorrect points format.");
            }
        }
    }

    private static int[] convertStringArrayToInt(String[] strings) {
        int[] result = new int[strings.length];
        for (int i = 0; i < strings.length; i++) {
            result[i] = Integer.parseInt(strings[i]);
        }
        return result;
    }

    private static void addStudent() {
        System.out.println("Enter student credentials or 'back' to return:");
        while (true) {
            String credentials = scanner.nextLine();
            if (credentials.equals("back")) {
                System.out.printf("Total %d students have been added.\n", studentMap.size());
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
                if (exist(email)) {
                    System.out.println("This email is already taken.");
                    continue;
                }
                Student student = new Student(firstName, lastName, email);
                studentMap.put(String.valueOf(Student.getNumStudents()), student);
                //numberOfStudents++;
                System.out.println("The student has been added.");
            }
        }
    }

    private static void list() {
        if (studentMap.size() == 0) {
            System.out.println("No students found");
            return;
        }
        System.out.println("Students:");
        for (String id : studentMap.keySet()) {
            System.out.println(id);
        }
    }

    private static boolean exist(String email) {
        for (Student student : studentMap.values()) {
            if (student.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
}

