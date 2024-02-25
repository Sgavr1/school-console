package org.example.service;

import org.example.dao.CourseDao;
import org.example.entity.Course;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseDao courseDao;

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
