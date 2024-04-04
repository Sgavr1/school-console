package org.example.service;

import jakarta.transaction.Transactional;
import org.example.dao.CourseDao;
import org.example.dto.CourseDto;
import org.example.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseDao courseDao;
    private final CourseMapper courseMapper;

    public CourseService(@Qualifier("JPACourse") CourseDao courseDao, CourseMapper courseMapper) {
        this.courseDao = courseDao;
        this.courseMapper = courseMapper;
    }

    @Transactional
    public void addCourse(CourseDto course) {
        courseDao.insert(courseMapper.toEntity(course));
    }

    @Transactional
    public void addCourses(List<CourseDto> courses) {
        courseDao.insertList(courses.stream().map(courseMapper::toEntity).toList());
    }

    @Transactional
    public CourseDto getCourseByName(String name) {
        return courseDao.getByName(name).map(courseMapper::toDto).orElse(null);
    }

    @Transactional
    public List<CourseDto> getAllCourses() {
        return courseDao.getAll().stream().map(courseMapper::toDto).toList();
    }

    @Transactional
    public boolean isEmpty() {
        return courseDao.isEmptyTable();
    }
}