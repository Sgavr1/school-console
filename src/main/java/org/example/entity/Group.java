package org.example.entity;

import org.example.map.Column;
import java.util.List;

public class Group {
    @Column(name = "group_id")
    private int id;
    @Column(name = "group_name")
    private String name;
    private List<Student> students;

    public Group() {

    }

    public Group(String name) {
        this.name = name;
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
