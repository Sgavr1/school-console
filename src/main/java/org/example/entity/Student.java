package org.example.entity;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private int groupId;
    private List<Course> courses;

    public Student() {
        courses = new ArrayList<>();
    }

    public Student(int id) {
        this.id = id;
        courses = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

}
