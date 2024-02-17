package org.example.entity;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private int id;
    private String name;
    private List<Student> students;

    public Group() {
        students = new ArrayList<>();
    }

    public Group(String name) {
        this.name = name;
        students = new ArrayList<>();
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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
