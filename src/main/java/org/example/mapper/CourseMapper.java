package org.example.mapper;

import org.example.dto.CourseDto;
import org.example.dto.StudentDto;
import org.example.entity.Course;
import org.example.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CourseMapper {
    CourseDto toDto(Course course);

    Course toEntity(CourseDto courseDto);

    @Mapping(target = "courses", ignore = true)
    @Mapping(source = "group.id", target = "groupId")
    StudentDto toStudentDto(Student student);
}