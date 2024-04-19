package org.example.service;

import org.example.dao.CourseDao;
import org.example.dto.CourseDto;
import org.example.mapper.CourseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class CourseService {
    private final Logger logger = LoggerFactory.getLogger(CourseService.class);
    private final CourseDao courseDao;
    private final CourseMapper courseMapper;

    public CourseService(CourseDao courseDao, CourseMapper courseMapper) {
        this.courseDao = courseDao;
        this.courseMapper = courseMapper;
    }

    public void addCourse(CourseDto course) {
        courseDao.save(courseMapper.toEntity(course));
    }

    public void addCourses(List<CourseDto> courses) {
        courseDao.saveAll(courses.stream().map(courseMapper::toEntity).toList());
    }

    public CourseDto getCourseByName(String name) {
        CourseDto courseDto = null;
        try {
            courseDto = courseDao.findByName(name).map(courseMapper::toDto).orElse(null);
        } catch (SQLException e) {
            logger.warn(String.format("Not found course by name = %s", name));
            e.printStackTrace();
        }

        return courseDto;
    }

    public List<CourseDto> getAllCourses() {
        return courseDao.findAll().stream().map(courseMapper::toDto).toList();
    }

    public boolean isEmpty() {
        return courseDao.findAll().isEmpty();
    }
}