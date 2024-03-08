package org.example.dao;

import org.example.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDao {

    void insert(Student student);

    void insertList(List<Student> students);

    Optional<Student> getById(int id);

    void delete(int id);

    void deleteFromAllCoursesByStudentId(int id);

    void deleteFromCourse(int studentId, int courseId);

    List<Student> getAll();

    List<Student> getStudentsByCourseName(String courseName);

    void insertStudentToCourse(int studentId, int courseId);

    void insertListStudentsOnCourse(int courseId, List<Student> students);

    boolean isEmptyTable();
}