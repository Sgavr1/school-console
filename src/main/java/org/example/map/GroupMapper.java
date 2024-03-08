package org.example.map;

import org.example.dto.GroupDto;
import org.example.dto.StudentDto;
import org.example.entity.Group;
import org.example.entity.Student;
import org.mapstruct.Mapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Mapper
public class GroupMapper implements RowMapper<Group> {
    private StudentMapper studentMapper;

    public void setStudentMapper(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Override
    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
        Group group = new Group();
        group.setName(rs.getString("group_name"));
        if (rs.wasNull()) {
            return null;
        }
        group.setId(rs.getInt("group_id"));

        Student student = studentMapper.mapRow(rs, rowNum);
        if (student != null) {
            group.getStudents().add(student);
        }

        return group;
    }

    public GroupDto toDto(Group group) {
        List<StudentDto> students = group.getStudents().stream().map(studentMapper::toDto).toList();

        return GroupDto.builder()
                .id(group.getId())
                .name(group.getName())
                .students(students)
                .build();
    }

    public Group toEntity(GroupDto groupDto) {
        Group group = new Group();
        group.setId(groupDto.getId());
        group.setName(groupDto.getName());
        group.setStudents(groupDto.getStudents().stream().map(studentMapper::toEntity).toList());

        return group;
    }
}