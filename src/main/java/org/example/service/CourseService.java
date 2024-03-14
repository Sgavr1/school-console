package org.example.service;

import org.example.dao.CourseDao;
import org.example.dto.CourseDto;
import org.example.mapper.CourseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseDao courseDao;
    private final CourseMapper courseMapper;

    public CourseService(CourseDao courseDao, CourseMapper courseMapper) {
        this.courseDao = courseDao;
        this.courseMapper = courseMapper;
    }

    public void addCourse(CourseDto course) {
        courseDao.insert(courseMapper.toEntity(course));
    }

    public void addCourses(List<CourseDto> courses) {
        courseDao.insertList(courses.stream().map(courseMapper::toEntity).toList());
    }

    public CourseDto getCourseByName(String name) {
        return courseDao.getByName(name).map(courseMapper::toDto).orElse(null);
    }

    public List<CourseDto> getAllCourses() {
        return courseDao.getAll().stream().map(courseMapper::toDto).toList();
    }

    public boolean isEmpty() {
        return courseDao.isEmptyTable();
    }
}
