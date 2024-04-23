package org.example.service;

import org.example.repository.CourseRepository;
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
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseDao, CourseMapper courseMapper) {
        this.courseRepository = courseDao;
        this.courseMapper = courseMapper;
    }

    public void addCourse(CourseDto course) {
        courseRepository.save(courseMapper.toEntity(course));
    }

    public void addCourses(List<CourseDto> courses) {
        courseRepository.saveAll(courses.stream().map(courseMapper::toEntity).toList());
    }

    public CourseDto getCourseByName(String name) {
        try {
            return courseRepository.findByName(name).map(courseMapper::toDto).orElse(null);
        } catch (SQLException e) {
            logger.warn(String.format("Not found course by name = %s", name));
            e.printStackTrace();
        }

        return null;
    }

    public List<CourseDto> getAllCourses() {
        return courseRepository.findAll().stream().map(courseMapper::toDto).toList();
    }

    public boolean isEmpty() {
        return courseRepository.findAll().isEmpty();
    }
}