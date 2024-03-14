package org.example.mapper;

import org.example.dto.CourseDto;
import org.example.entity.Course;
import org.mapstruct.Mapper;

@Mapper
public interface CourseMapper {
    CourseDto toDto(Course course);

    Course toEntity(CourseDto courseDto);
}