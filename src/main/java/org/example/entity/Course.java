package org.example.entity;

import org.example.map.Column;

import java.util.List;

public class Course {
    @Column(name = "course_id")
    private int id;
    @Column(name = "course_name")
    private String name;
    @Column(name = "course_description")
    private String description;
    private List<Student> students;

    public Course() {

    }

    public Course(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}