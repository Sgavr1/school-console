package org.example.mapper;

import org.example.dto.CourseDto;
import org.example.entity.Course;
import org.mapstruct.*;

@Mapper(uses = {StudentMapperIgnoreCourse.class})
public interface CourseMapper {
    @Mapping(source = "students", target = "students", qualifiedByName = "mapStudentsDto")
    CourseDto toDto(Course course);

    @Mapping(source = "students", target = "students", qualifiedByName = "mapStudents")
    Course toEntity(CourseDto courseDto);
}