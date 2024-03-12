package org.example.map.dto;

import org.example.dto.StudentDto;
import org.example.entity.Student;
import org.mapstruct.Mapper;

@Mapper
public interface StudentMapper {
    StudentDto toDto(Student student);

    Student toEntity(StudentDto studentDto);
}