package org.example.service;

import org.example.dao.StudentDao;
import org.example.entity.Student;

import java.util.List;

public class StudentService {
    private StudentDao studentDao;

    public StudentService(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public void addStudent(Student student) {
        studentDao.insert(student);
    }

    public void addStudents(List<Student> students) {
        studentDao.insertList(students);
    }

    public Student getStudentById(int id) {
        return studentDao.getById(id).orElse(null);
    }

    public List<Student> getStudentsByCourseName(String courseName) {
        return studentDao.getStudentsByCourseName(courseName);
    }

    public void delete(Student student) {
        studentDao.delete(student.getId());
    }

    public List<Student> getStudents() {
        return studentDao.getAll();
    }

    public void addStudentToCourse(int studentId, int courseId) {
        studentDao.insertStudentToCourse(studentId, courseId);
    }

    public void addStudentsOnCourse(int courseId, List<Student> students) {
        studentDao.insertListStudentsOnCourse(courseId, students);
    }
}