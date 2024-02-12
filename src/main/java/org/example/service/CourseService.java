package org.example.service;

import org.example.dao.CourseDao;
import org.example.dao.StudentDao;
import org.example.entity.Course;

import java.util.List;

public class CourseService {
    private CourseDao courseDao;
    private StudentDao studentDao;

    public CourseService(CourseDao courseDao, StudentDao studentDao) {
        this.courseDao = courseDao;
        this.studentDao = studentDao;
    }

    public void addCourse(Course course) {
        courseDao.insert(course);
    }

    public void addCourses(List<Course> courses) {
        courseDao.insertList(courses);
    }

    public Course getCourseByName(String name) {
        return courseDao.getByName(name).orElse(null);
    }

    public List<Course> getAllCourses() {
        List<Course> courses = courseDao.getAll();
        for (Course course : courses) {
            course.setStudents(studentDao.getStudentsByCourseId(course.getId()));
        }

        return courses;
    }
}
