package org.example.service;

import org.example.dao.CourseDao;
import org.example.dao.StudentDao;
import org.example.entity.Course;

import java.util.List;

public class CourseService {
    private CourseDao courseDao;

    public CourseService(CourseDao courseDao) {
        this.courseDao = courseDao;
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
        return courseDao.getAll();
    }
}
