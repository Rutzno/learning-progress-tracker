package com.diarpy;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Mack_TB
 * @since 24/06/2022
 * @version 1.0.4
 */

public class Student {
    private int ID;
    private String firstName;
    private String lastName;
    private String email;
    private final Map<Course, Integer> courses;
    private static int numStudents;

    static {
        numStudents = 9999;
    }

    public Student(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        ID = ++numStudents;
        courses = new LinkedHashMap<>();
    }

    public boolean grade(int... notes) {
        try {
            if (notes.length != 4 || notes[0] < 0 || notes[1] < 0 || notes[2] < 0 || notes[3] < 0) {
                System.out.println("Incorrect points format.");
                return false;
            }
           /* for (int n : notes) {
                if (n < 0) {
                    System.out.println("Incorrect points format.");
                    return;
                }
            }*/
            int i = 0;
            for (Course course : Main.courseMap.values()) {
                if (notes[i] != 0) {
                    course.setActivityCount(course.getActivityCount() + 1);
                    course.getStudents().add(this);
                }
                int note = courses.getOrDefault(course, 0) + notes[i];
                courses.put(course, note);
                i++;
            }
            return true;

        } catch (Exception e) {
            System.out.println("Incorrect points format.");
            return false;
        }
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<Course, Integer> getCourses() {
        return courses;
    }

    public static int getNumStudents() {
        return numStudents;
    }
}
