package org.example.mapper;

import org.example.dto.CourseDto;
import org.example.dto.GroupDto;
import org.example.dto.StudentDto;
import org.example.entity.Course;
import org.example.entity.Group;
import org.example.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface StudentMapper {
    @Mapping(source = "group.id", target = "groupId")
    StudentDto toDto(Student student);

    Student toEntity(StudentDto studentDto);

    @Mapping(target = "students", ignore = true)
    GroupDto toGroupDto(Group group);

    @Mapping(target = "students", ignore = true)
    CourseDto toCourseDto(Course course);
}