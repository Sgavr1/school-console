package org.example.configuration;

import org.example.map.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {
    @Bean
    public StudentMapper getStudentMapper() {
        StudentMapper studentMapper = new StudentMapper();
        CourseMapper courseMapper = new CourseMapper();
        studentMapper.setCourseMapper(courseMapper);
        return studentMapper;
    }

    @Bean
    public CourseMapper getCourseMapper() {
        CourseMapper courseMapper = new CourseMapper();
        StudentMapper studentMapper = new StudentMapper();
        courseMapper.setStudentMapper(studentMapper);
        return courseMapper;
    }

    @Bean
    public GroupMapper getGroupMapper() {
        GroupMapper groupMapper = new GroupMapper();
        StudentMapper studentMapper = new StudentMapper();
        groupMapper.setStudentMapper(studentMapper);
        return groupMapper;
    }

    @Bean
    public StudentDtoMapper getStudentDtoMapper() {
        StudentDtoMapper studentDtoMapper = new StudentDtoMapper();
        studentDtoMapper.setCourseDtoMapper(new CourseDtoMapper());
        return studentDtoMapper;
    }

    @Bean
    public CourseDtoMapper getCourseDtoMapper() {
        CourseDtoMapper courseDtoMapper = new CourseDtoMapper();
        courseDtoMapper.setStudentDtoMapper(new StudentDtoMapper());
        return courseDtoMapper;
    }

    @Bean
    public GroupDtoMapper getGroupDtoMapper() {
        GroupDtoMapper groupDtoMapper = new GroupDtoMapper();
        groupDtoMapper.setStudentDtoMapper(new StudentDtoMapper());
        return groupDtoMapper;
    }
}
