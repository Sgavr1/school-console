package org.example.dao;

import org.example.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseDao {

    void insert(Course course);

    void insertList(List<Course> courses);

    List<Course> getAll();

    Optional<Course> getByName(String name);

    List<Course> getCoursesByStudentId(int studentId);

    boolean isEmptyTable();
}