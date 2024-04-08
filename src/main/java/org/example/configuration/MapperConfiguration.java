package org.example.configuration;

import org.example.mapper.*;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {
    @Bean
    public StudentMapper getStudentMapper() {
        return Mappers.getMapper(StudentMapper.class);
    }

    @Bean
    public CourseMapper getCourseMapper() {
        return Mappers.getMapper(CourseMapper.class);
    }

    @Bean
    public GroupMapper getGroupMapper() {
        return Mappers.getMapper(GroupMapper.class);
    }

    @Bean
    public StudentMapperIgnoreCourse getStudentMapperIgnoreCourse() {
        return Mappers.getMapper(StudentMapperIgnoreCourse.class);
    }

    @Bean
    public CourseMapperIgnoreStudent getCourseMapperIgnoreStudent() {
        return Mappers.getMapper(CourseMapperIgnoreStudent.class);
    }
}
