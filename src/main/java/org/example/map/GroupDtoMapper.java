package org.example.map;

import org.example.dto.GroupDto;
import org.example.dto.StudentDto;
import org.example.entity.Group;

import java.util.List;

public class GroupDtoMapper {
    private StudentDtoMapper studentDtoMapper;

    public void setStudentDtoMapper(StudentDtoMapper studentDtoMapper) {
        this.studentDtoMapper = studentDtoMapper;
    }

    public GroupDto mapGroupToDto(Group group) {
        List<StudentDto> students = group.getStudents().stream().map(studentDtoMapper::mapStudentToDto).toList();

        return GroupDto.builder()
                .id(group.getId())
                .name(group.getName())
                .students(students)
                .build();
    }

    public Group mapDtoToGroup(GroupDto groupDto) {
        Group group = new Group();
        group.setId(groupDto.getId());
        group.setName(groupDto.getName());
        group.setStudents(groupDto.getStudents().stream().map(studentDtoMapper::mapDtoToStudent).toList());

        return group;
    }
}