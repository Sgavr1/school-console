package org.example.Service;

import org.example.Dao.StudentCourseDao;
import org.example.Entity.StudentCourse;

import java.util.List;

public class StudentCourseService {

    private StudentCourseDao studentCourseDao;

    public StudentCourseService(StudentCourseDao studentCourseDao) {
        this.studentCourseDao = studentCourseDao;
    }

    public void addStudentToCourse(StudentCourse studentCourse) {
        studentCourseDao.insert(studentCourse);
    }

    public void addStudentToCourses(List<StudentCourse> studentCourses) {
        studentCourseDao.insertList(studentCourses);
    }

    public void deleteStudentFromCourse(StudentCourse studentCourse) {
        studentCourseDao.deleteStudentCourse(studentCourse);
    }

}
