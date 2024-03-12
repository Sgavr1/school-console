package org.example.map.dto;

import org.example.dto.GroupDto;
import org.example.entity.Group;
import org.mapstruct.Mapper;


@Mapper
public interface GroupMapper {
    GroupDto toDto(Group group);

    Group toEntity(GroupDto groupDto);
}