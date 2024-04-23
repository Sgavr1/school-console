package org.example.mapper;

import org.example.dto.StudentDto;
import org.example.entity.Student;
import org.mapstruct.*;

@Mapper(uses = {CourseMapperIgnoreStudent.class})
public interface StudentMapper {
    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "courses", target = "courses", qualifiedByName = "mapCoursesDto")
    StudentDto toDto(Student student);

    @Mapping(source = "groupId", target = "group.id")
    @Mapping(source = "courses", target = "courses", qualifiedByName = "mapCourses")
    Student toEntity(StudentDto studentDto);
}