package org.example.service;

import org.example.dao.CourseDao;
import org.example.dao.StudentDao;
import org.example.entity.Course;
import org.example.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private StudentDao studentDao;
    private CourseDao courseDao;

    public StudentService(StudentDao studentDao, CourseDao courseDao) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
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
        Course course = courseDao.getByName(courseName).orElse(null);

        if(course != null){
            return studentDao.getStudentsByCourseId(course.getId());
        }

        return new ArrayList<>();
    }

    public void delete(Student student) {
        studentDao.deleteFromAllCoursesByStudentId(student.getId());
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