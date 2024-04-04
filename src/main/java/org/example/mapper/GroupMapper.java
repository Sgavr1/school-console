package org.example.mapper;

import org.example.dto.GroupDto;
import org.example.dto.StudentDto;
import org.example.entity.Group;
import org.example.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper
public interface GroupMapper {
    GroupDto toDto(Group group);

    Group toEntity(GroupDto groupDto);

    @Mapping(source = "group.id", target = "groupId")
    @Mapping(target = "courses", ignore = true)
    StudentDto toStudentDto(Student student);
}