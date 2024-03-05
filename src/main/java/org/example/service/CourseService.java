package org.example.service;

import org.example.dao.CourseDao;
import org.example.dto.CourseDto;
import org.example.entity.Course;
import org.example.map.CourseDtoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseDao courseDao;
    private final CourseDtoMapper courseDtoMapper;

    public CourseService(CourseDao courseDao, CourseDtoMapper courseDtoMapper) {
        this.courseDao = courseDao;
        this.courseDtoMapper = courseDtoMapper;
    }

    public void addCourse(CourseDto course) {
        courseDao.insert(courseDtoMapper.mapDtoToCourse(course));
    }

    public void addCourses(List<CourseDto> courses) {
        courseDao.insertList(courses.stream().map(courseDtoMapper::mapDtoToCourse).toList());
    }

    public CourseDto getCourseByName(String name) {
        return courseDao.getByName(name).map(courseDtoMapper::mapCourseToDto).orElse(null);
    }

    public List<CourseDto> getAllCourses() {
        return courseDao.getAll().stream().map(courseDtoMapper::mapCourseToDto).toList();
    }
}
