package org.example.mapper;

import org.example.dto.GroupDto;
import org.example.entity.Group;
import org.mapstruct.*;


@Mapper(componentModel = "spring", uses = {StudentMapperIgnoreCourse.class})
public interface GroupMapper {
    @Mapping(source = "students", target = "students", qualifiedByName = "mapStudentsDto")
    GroupDto toDto(Group group);

    @Mapping(source = "students", target = "students", qualifiedByName = "mapStudents")
    Group toEntity(GroupDto groupDto);
}