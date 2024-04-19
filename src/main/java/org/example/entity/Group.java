package org.example.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Integer id;
    @Column(name = "group_name")
    private String name;
    @OneToMany(mappedBy = "group")
    private List<Student> students;

    public Group() {
        students = new ArrayList<>();
    }

    public Group(int id) {
        this.id = id;
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
