package com.diarpy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @author Mack_TB
 * @since 24/06/2022
 * @version 1.0.5
 */

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static String nameRegex = "[A-Za-z\\s]+([A-Za-z]|[-'][A-Za-z])+";
    static String emailRegex = "[a-z.\\d]+@[a-z\\d]+\\.[a-z\\d]+";
    static Map<String, Student> studentMap = new LinkedHashMap<>();
    static Map<String, Course> courseMap = new LinkedHashMap<>();
    static List<Message> messageList = new ArrayList<>();
    static List<Message> oldNotifications = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Learning Progress Tracker");
        createCourses();
        processCommand();
    }

    private static String input() {
        System.out.print("> ");
        return scanner.nextLine();
    }

    private static void createCourses() {
        Course course1 = new Course("Java", 600);
        Course course2 = new Course("DSA", 400);
        Course course3 = new Course("Databases", 480);
        Course course4 = new Course("Spring", 550);
        courseMap.put("Java", course1);
        courseMap.put("DSA", course2);
        courseMap.put("Databases", course3);
        courseMap.put("Spring", course4);
    }

    public static void processCommand() {
        String input;
        do {
            input = input();
            if (input.equalsIgnoreCase("add students")) {
                addStudent();
            } else if (input.equalsIgnoreCase("list")) {
                list();
            } else if (input.equalsIgnoreCase("add points")) {
                addPoints();
            } else if (input.equalsIgnoreCase("find")) {
                findStudent();
            } else if (input.equalsIgnoreCase("statistics")) {
                statistics();
            } else if (input.equalsIgnoreCase("notify")) {
                notifyStudents();
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

    private static void notifyStudents() {
        Set<String> tempEmailList = new HashSet<>();
        messageList.forEach(message -> {
            tempEmailList.add(message.getTo());
            System.out.println(message);
            System.out.println("------------------------------------------------------------");
        });
        System.out.printf("Total %d students have been notified\n", tempEmailList.size());
        oldNotifications.addAll(messageList);
        messageList.clear();
    }

    private static void findStudent() {
        System.out.println("Enter an id or 'back' to return:");
        while (true) {
            String id = input();
            if (id.equalsIgnoreCase("back")) {
                break;
            }
            if (!studentMap.containsKey(id)) {
                System.out.printf("No student is found for id=%s.\n", id);
            } else {
                Student student = studentMap.get(id);
                System.out.printf("%s points: Java=%d; DSA=%d; Databases=%d; Spring=%d\n",
                        id,
                        student.getCourses().getOrDefault(courseMap.get("Java"), 0),
                        student.getCourses().getOrDefault(courseMap.get("DSA"), 0),
                        student.getCourses().getOrDefault(courseMap.get("Databases"), 0),
                        student.getCourses().getOrDefault(courseMap.get("Spring"), 0)
                );
            }
        }
    }

    private static void addPoints() {
        System.out.println("Enter an id and points or 'back' to return:");
        while (true) {
            String idnPoints = input();
            if (idnPoints.equalsIgnoreCase("back")) {
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
            String credentials = input();
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

    private static void statistics() {
        System.out.println("Type the name of a course to see details or 'back' to quit:");
        System.out.println("Most popular: " + mostPopular());
        System.out.println("Least popular: " + leastPopular());
        System.out.println("Highest activity: " + highestActivity());
        System.out.println("Lowest activity: " + lowestActivity());
        System.out.println("Easiest course: " + easiestCourse());
        System.out.println("Hardest course: " + hardestCourse());
        while (true) {
            String courseName = input();
            if (courseName.equals("back")) {
                break;
            }
            courseName = containsKey(courseName);
            if (!courseMap.containsKey(courseName)) {
                System.out.println("Unknown course.");
            } else {
                Course course = courseMap.get(courseName);
                List<Student> students = new ArrayList<>(course.getStudents());
                students.sort(Comparator.comparing((Student student1) -> student1.getCourses().get(course))
                        .reversed()
                        .thenComparing(Student::getID)
                );
                System.out.println(courseName);
                System.out.println("id\t\tpoints\tcompleted");
                for (Student student : students) {
                    float p = (float) student.getCourses().get(course) / course.getPoints() * 100;
                    BigDecimal bd = new BigDecimal(p).setScale(1, RoundingMode.HALF_UP);
//                    String df = new DecimalFormat("#.#").format(bd);
                    String strNum = String.format("%.1f", bd).replace(",", ".");
                    System.out.printf("%d\t%d\t\t%s%%\n", student.getID(), student.getCourses().get(course), strNum);
                }
            }
        }
    }

    private static String containsKey(String courseName) {
        for (String name : courseMap.keySet()) {
            if (name.equalsIgnoreCase(courseName)) {
                return name;
            }
        }
        return "";
    }

    private static String getResult(StringBuilder result, List<Course> courses, int m) {
        for (Course course : courses) {
            if (course.enrolledStudentsCount() == m) {
                result.append(course.getName()).append(", ");
            }
        }
        if (result.length() >= 2) {
            result.delete(result.length()-2, result.length());
        }
        return result.length() != 0 ? result.toString() : "n/a";
    }

    // enrolled students >
    private static String mostPopular() {
        StringBuilder result = new StringBuilder();
        List<Course> courses = new ArrayList<>(courseMap.values());
        courses.sort(Comparator.comparing(Course::enrolledStudentsCount));
        int max = courses.get(courses.size() - 1).enrolledStudentsCount();
        if (max == 0) return "n/a";
        return getResult(result, courses, max);
    }

    // enrolled students < 0
    private static String leastPopular() {
        StringBuilder result = new StringBuilder();
        List<Course> courses = new ArrayList<>(courseMap.values());
        courses.sort(Comparator.comparing(Course::enrolledStudentsCount));
        int min = courses.get(0).enrolledStudentsCount();
        int max = courses.get(courses.size() - 1).enrolledStudentsCount();
        if (min == max) return "n/a";
        return getResult(result, courses, min);
    }

    private static String getResultActivity(StringBuilder result, List<Course> courses, int m) {
        for (Course course : courses) {
            if (course.getActivityCount() == m) {
                result.append(course.getName()).append(", ");
            }
        }
        if (result.length() >= 2) {
            result.delete(result.length()-2, result.length());
        }
        return result.length() != 0 ? result.toString() : "n/a";
    }

    // number of submissions >
    private static String highestActivity() {
        StringBuilder result = new StringBuilder();
        List<Course> courses = new ArrayList<>(courseMap.values());
        courses.sort(Comparator.comparing(Course::getActivityCount));
        int max = courses.get(courses.size() - 1).getActivityCount();
        if (max == 0) return "n/a";
        return getResultActivity(result, courses, max);
    }

    // submissions < 0
    private static String lowestActivity() {
        StringBuilder result = new StringBuilder();
        List<Course> courses = new ArrayList<>(courseMap.values());
        courses.sort(Comparator.comparing(Course::getActivityCount));
        int min = courses.get(0).getActivityCount();
        int max = courses.get(courses.size() - 1).getActivityCount();
        if (min == max) return "n/a";
        return getResultActivity(result, courses, min);
    }

    private static String getResultAverageScore(StringBuilder result, List<Course> courses, double m) {
        for (Course course : courses) {
            if (course.averageScore() == m) {
                result.append(course.getName()).append(", ");
            }
        }
        if (result.length() >= 2) {
            result.delete(result.length()-2, result.length());
        }
        return result.length() != 0 ? result.toString() : "n/a";
    }

    // average score >
    private static String easiestCourse() {
        StringBuilder result = new StringBuilder();
        List<Course> courses = new ArrayList<>(courseMap.values());
        courses.sort(Comparator.comparing(Course::averageScore));
        double max = courses.get(courses.size() - 1).averageScore();
        if (max == 0) return "n/a";
        return getResultAverageScore(result, courses, max);
    }

    // average score <
    private static String hardestCourse() {
        StringBuilder result = new StringBuilder();
        List<Course> courses = new ArrayList<>(courseMap.values());
        courses.sort(Comparator.comparing(Course::averageScore));
        double min = courses.get(0).averageScore();
        double max = courses.get(courses.size() - 1).averageScore();
        if (min == max) return "n/a";
        return getResultAverageScore(result, courses, min);
    }
}
