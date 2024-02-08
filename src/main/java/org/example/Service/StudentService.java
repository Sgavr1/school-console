package org.example.Service;

import org.example.Dao.CourseDao;
import org.example.Dao.StudentCourseDao;
import org.example.Dao.StudentDao;
import org.example.Entity.Course;
import org.example.Entity.Student;

import java.util.List;

public class StudentService {
    private StudentDao studentDao;
    private CourseDao courseDao;
    private StudentCourseDao studentCourseDao;

    public StudentService(StudentDao studentDao, CourseDao courseDao, StudentCourseDao studentCourseDao) {
        this.studentDao = studentDao;
        this.studentCourseDao = studentCourseDao;
        this.courseDao = courseDao;
    }

    public void addStudent(Student student) {
        studentDao.insert(student);
    }

    public void addStudents(List<Student> students) {
        studentDao.insertList(students);
    }

    public Student getStudentById(int id) {
        return studentDao.getById(id).orElse(new Student());
    }

    public List<Student> getStudentsByCourseName(String courseName) {
        Course course = courseDao.getByName(courseName).get();

        return studentCourseDao.getAllStudentByCourseId(course.getId()).stream()
                .map(studentCourse -> studentDao.getById(studentCourse.getStudentId()).get())
                .toList();
    }

    public void delete(Student student) {
        studentCourseDao.deleteByStudent(student.getId());
        studentDao.delete(student.getId());
    }

    public List<Student> getStudents() {
        return studentDao.getAll();
    }
}