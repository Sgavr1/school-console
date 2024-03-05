package org.example.dto;


import java.util.ArrayList;
import java.util.List;

public class GroupDto {
    private int id;
    private String name;
    private List<StudentDto> students;

    public GroupDto() {
        students = new ArrayList<>();
    }

    public GroupDto(int id, String name, List<StudentDto> students) {
        this.id = id;
        this.name = name;
        this.students = students;
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

    public List<StudentDto> getStudents() {
        return students;
    }

    public void setStudents(List<StudentDto> students) {
        this.students = students;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int id;
        private String name;
        private List<StudentDto> students;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder students(List<StudentDto> students) {
            this.students = students;
            return this;
        }

        public GroupDto build() {
            return new GroupDto(id, name, students);
        }
    }
}
