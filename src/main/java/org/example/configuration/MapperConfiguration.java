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
}
