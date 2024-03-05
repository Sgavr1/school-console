package org.example.dto;

import java.util.ArrayList;
import java.util.List;

public class CourseDto {
    private int id;
    private String name;
    private String description;
    private List<StudentDto> students;

    public CourseDto() {
        students = new ArrayList<>();
    }

    public CourseDto(int id, String name, String description, List<StudentDto> students) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        private String description;
        private List<StudentDto> students;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder students(List<StudentDto> students) {
            this.students = students;
            return this;
        }

        public CourseDto build() {
            return new CourseDto(id, name, description, students);
        }
    }
}
