package org.example.dto;

import java.util.ArrayList;
import java.util.List;

public class StudentDto {
    private int id;
    private String firstName;
    private String lastName;
    private int groupId;
    private List<CourseDto> courses;

    public StudentDto() {
        courses = new ArrayList<>();
    }

    public StudentDto(int id, String firstName, String lastName, int groupId, List<CourseDto> courses) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupId = groupId;
        this.courses = courses;
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

    public List<CourseDto> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseDto> courses) {
        this.courses = courses;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int id;
        private String firstName;
        private String lastName;
        private int groupId;
        private List<CourseDto> courses;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder groupId(int groupId) {
            this.groupId = groupId;
            return this;
        }

        public Builder courses(List<CourseDto> courses) {
            this.courses = courses;
            return this;
        }

        public StudentDto build() {
            return new StudentDto(id, firstName, lastName, groupId, courses);
        }
    }
}