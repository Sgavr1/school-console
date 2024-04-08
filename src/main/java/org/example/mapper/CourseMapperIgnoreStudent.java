package org.example.mapper;

import org.example.dto.CourseDto;
import org.example.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface CourseMapperIgnoreStudent {
    @Named("mapCoursesDto")
    @Mapping(target = "students", ignore = true)
    CourseDto toDtoIgnoreStudents(Course course);

    @Named("mapCourses")
    @Mapping(target = "students", ignore = true)
    Course toEntityIgnoreStudents(CourseDto courseDto);
}
