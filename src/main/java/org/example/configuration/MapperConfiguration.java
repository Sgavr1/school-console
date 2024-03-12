package org.example.configuration;

import org.example.map.dto.CourseMapper;
import org.example.map.dto.GroupMapper;
import org.example.map.dto.StudentMapper;
import org.example.map.row.CourseRowMapper;
import org.example.map.row.GroupRowMapper;
import org.example.map.row.StudentRowMapper;
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
    public StudentRowMapper getStudentRowMapper(){
        StudentRowMapper studentRowMapper = new StudentRowMapper();
        studentRowMapper.setCourseMapper(new CourseRowMapper());

        return studentRowMapper;
    }

    @Bean
    public CourseRowMapper getCourseRowMapper(){
        CourseRowMapper courseRowMapper = new CourseRowMapper();
        courseRowMapper.setStudentMapper(new StudentRowMapper());

        return courseRowMapper;
    }

    @Bean
    public GroupRowMapper getGroupRowMapper(){
        GroupRowMapper groupRowMapper = new GroupRowMapper();
        groupRowMapper.setStudentMapper(new StudentRowMapper());

        return groupRowMapper;
    }
}
