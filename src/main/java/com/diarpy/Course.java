package com.diarpy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Course {
    private String name;
    private int points;
    private Set<Student> students;

    private int activityCount;

    public Course(String name, int points) {
        this.name = name;
        this.points = points;
        students = new HashSet<>();
        activityCount = 0;
    }

    public int enrolledStudentsCount() {
        return students.size();
    }

    /*public int activityCount() {
        return 0;
    }*/

    public double averageScore() {
        int sum = 0;
        for (Student student : students) {
            sum += student.getCourses().get(this);
        }
        return (double) sum / activityCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public int getActivityCount() {
        return activityCount;
    }

    public void setActivityCount(int activityCount) {
        this.activityCount = activityCount;
    }
}
