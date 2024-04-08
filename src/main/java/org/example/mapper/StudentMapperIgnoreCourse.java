package org.example.mapper;

import org.example.dto.StudentDto;
import org.example.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface StudentMapperIgnoreCourse {
    @Named("mapStudentsDto")
    @Mapping(target = "courses", ignore = true)
    @Mapping(source = "group.id", target = "groupId")
    StudentDto toDtoIgnoreCourses(Student student);

    @Named("mapStudents")
    @Mapping(target = "courses", ignore = true)
    @Mapping(source = "groupId", target = "group.id")
    Student toEntityIgnoreCourses(StudentDto studentDto);
}
